package ma.fstt.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.dao.LigneCommandeDAO;
import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;
import ma.fstt.entities.LigneDeCommande;
import ma.fstt.service.ClientService;
import ma.fstt.service.CommandeService;
import ma.fstt.entities.Produit;
import ma.fstt.service.ProduitService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(value = "/lignes")
public class LigneCommandeController extends HttpServlet {

    @Inject
    private CommandeService commandeService;

    @Inject
    private ClientService clientService;

    @Inject
    private LigneCommandeDAO ligneCommandeDAO;

    @Inject
    private ProduitService produitService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientParam = req.getParameter("clientId");
        if (clientParam == null) {
            req.setAttribute("error", "Paramètre clientId manquant");
            req.getRequestDispatcher("/views/commande/lignes.jsp").forward(req, resp);
            return;
        }
        try {
            long clientId = Long.parseLong(clientParam);

            Client probe = new Client();
            probe.setClient_id(clientId);
            Client client = clientService.findOne(probe);
            if (client == null) {
                req.setAttribute("error", "Client introuvable: ID=" + clientId);
                req.getRequestDispatcher("/views/commande/lignes.jsp").forward(req, resp);
                return;
            }

            List<Commande> all = commandeService.findAll();
            java.util.List<Commande> commandesDuClient = new java.util.ArrayList<>();
            for (Commande c : all) {
                if (c.getClient_id() != null && c.getClient_id().getClient_id() != null && c.getClient_id().getClient_id() == clientId) {
                    commandesDuClient.add(c);
                }
            }

            Map<Long, List<LigneDeCommande>> lignesMap = new HashMap<>();
            for (Commande cmd : commandesDuClient) {
                lignesMap.put(cmd.getCommande_id(), ligneCommandeDAO.findByCommandeId(cmd.getCommande_id()));
            }

            req.setAttribute("client", client);
            req.setAttribute("commandes", commandesDuClient);
            req.setAttribute("lignesMap", lignesMap);
            req.getRequestDispatcher("/views/commande/lignes.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/views/commande/lignes.jsp").forward(req, resp);
        } catch (NumberFormatException nfe) {
            req.setAttribute("error", "clientId invalide");
            req.getRequestDispatcher("/views/commande/lignes.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (!"add-line".equals(action) && !"delete-line".equals(action)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action invalide");
            return;
        }
        String clientParam = req.getParameter("clientId");
        String commandeParam = req.getParameter("commandeId");
        String produitParam = req.getParameter("produitId");
        String quantiteParam = req.getParameter("quantite");
        String prixParam = req.getParameter("prix_unitaire");
        try {
            long clientId = Long.parseLong(clientParam);
            long commandeId = Long.parseLong(commandeParam);
            if ("add-line".equals(action)) {
                long produitId = Long.parseLong(produitParam);
                int quantite = Integer.parseInt(quantiteParam);
                double prixUnitaire = 0.0;
                if (prixParam != null && !prixParam.isBlank()) {
                    prixUnitaire = Double.parseDouble(prixParam);
                }

            // validate client
            Client probe = new Client();
            probe.setClient_id(clientId);
            if (clientService.findOne(probe) == null) {
                req.setAttribute("error", "Client introuvable");
                doGet(req, resp);
                return;
            }
            // validate commande belongs to client
            boolean commandeOk = false;
            for (Commande c : commandeService.findAll()) {
                if (c.getCommande_id() != null && c.getCommande_id() == commandeId && c.getClient_id() != null && c.getClient_id().getClient_id() != null && c.getClient_id().getClient_id() == clientId) {
                    commandeOk = true;
                    break;
                }
            }
            if (!commandeOk) {
                req.setAttribute("error", "Commande invalide pour ce client");
                doGet(req, resp);
                return;
            }
            // validate produit
            Produit pProbe = new Produit();
            pProbe.setId(produitId);
            Produit produit = produitService.findOne(pProbe);
            if (produit == null) {
                req.setAttribute("error", "Produit introuvable");
                doGet(req, resp);
                return;
            }
            // auto-fill price from product if not provided
            if (prixUnitaire == 0.0) {
                prixUnitaire = produit.getPrix();
            }
            // stock check
            if (produit.getStock() < quantite) {
                req.setAttribute("error", "Stock insuffisant pour le produit " + produitId + " (disponible: " + produit.getStock() + ")");
                doGet(req, resp);
                return;
            }

            LigneDeCommande ligne = new LigneDeCommande(null, commandeId, produitId, quantite, prixUnitaire);
            ligneCommandeDAO.create(ligne);
            // decrement stock
            produit.setStock(produit.getStock() - quantite);
            produitService.update(produit);

            // recompute total
            double total = ligneCommandeDAO.sumTotalByCommandeId(commandeId);
            Commande update = new Commande();
            update.setCommande_id(commandeId);
            update.setDate_commande(null);
            update.setClient_id(probe);
            update.setTotal(total);
            // we need full entity to avoid nulling date/client; fetch existing then update total only
            for (Commande c : commandeService.findAll()) {
                if (c.getCommande_id() == commandeId) {
                    c.setTotal(total);
                    commandeService.update(c);
                    break;
                }
            }

            resp.sendRedirect(req.getContextPath() + "/lignes?clientId=" + clientId);
            } else if ("delete-line".equals(action)) {
                long ligneId = Long.parseLong(req.getParameter("ligneId"));
                // fetch line to know produit and quantite for restock
                LigneDeCommande existing = ligneCommandeDAO.findOne(new LigneDeCommande(ligneId, null, null, 0, 0));
                if (existing != null) {
                    // restock product
                    Produit p = new Produit();
                    p.setId(existing.getProduitId());
                    Produit full = produitService.findOne(p);
                    if (full != null) {
                        full.setStock(full.getStock() + existing.getQuantite());
                        produitService.update(full);
                    }
                    // delete line
                    ligneCommandeDAO.delete(existing);
                    // recompute total
                    double total = ligneCommandeDAO.sumTotalByCommandeId(commandeId);
                    for (Commande c : commandeService.findAll()) {
                        if (c.getCommande_id() == commandeId) {
                            c.setTotal(total);
                            commandeService.update(c);
                            break;
                        }
                    }
                }
                resp.sendRedirect(req.getContextPath() + "/lignes?clientId=" + clientId);
            }
        } catch (SQLException | NumberFormatException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }
}


