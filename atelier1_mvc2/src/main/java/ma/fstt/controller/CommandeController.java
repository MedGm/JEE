package ma.fstt.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fstt.service.CommandeService;
import ma.fstt.service.ClientService;
import ma.fstt.dao.LigneCommandeDAO;
import ma.fstt.entities.Commande;
import ma.fstt.entities.Client;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(value="/commandes")
public class CommandeController extends HttpServlet {

    @Inject
	private CommandeService commandeService;

	@Inject
	private ClientService clientService;

	@Inject
	private LigneCommandeDAO ligneCommandeDAO;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			List<Commande> commandes = commandeService.findAll();
			req.setAttribute("commandes", commandes);
			String detailsId = req.getParameter("commandeId");
			if (detailsId != null) {
				long cid = Long.parseLong(detailsId);
				req.setAttribute("lignes", ligneCommandeDAO.findByCommandeId(cid));
			}
			req.getRequestDispatcher("/views/commande/list.jsp").forward(req, resp);
		} catch (SQLException e) {
			req.setAttribute("error", e.getMessage());
			req.getRequestDispatcher("/views/commande/list.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		try {
			if ("add".equals(action)) {
				String date_commande = req.getParameter("date_commande");
				if (date_commande != null && date_commande.contains("T")) {
					date_commande = date_commande.replace('T', ' ') + ":00";
				}
				Long client_id = Long.parseLong(req.getParameter("client_id"));
				// validate client exists to avoid FK violation
				ma.fstt.entities.Client probe = new ma.fstt.entities.Client();
				probe.setClient_id(client_id);
				ma.fstt.entities.Client existing = clientService.findOne(probe);
				if (existing == null) {
					req.setAttribute("error", "Client introuvable: ID=" + client_id);
					req.setAttribute("commandes", commandeService.findAll());
					req.getRequestDispatcher("/views/commande/list.jsp").forward(req, resp);
					return;
				}
				double total = 0.0;
				Client client = new Client();
				client.setClient_id(client_id);
				Commande commande = new Commande(null, date_commande, client, total);
				try {
					commandeService.create(commande);
				} catch (java.sql.SQLIntegrityConstraintViolationException fkEx) {
					req.setAttribute("error", "Violation de contrainte: " + fkEx.getMessage());
					req.setAttribute("commandes", commandeService.findAll());
					req.getRequestDispatcher("/views/commande/list.jsp").forward(req, resp);
					return;
				}
			}
			resp.sendRedirect(req.getContextPath() + "/commandes");
		} catch (SQLException e) {
			req.setAttribute("error", e.getMessage());
			req.getRequestDispatcher("/views/commande/list.jsp").forward(req, resp);
		}
	}
}
