package ma.fstt.entities;

public class Commande {

    private Long commande_id;
    private String date_commande;
    private Client client_id;
    private double total;

    public Commande() {}

    public Commande(Long commande_id, String date_commande, Client client_id, double total) {
        this.commande_id = commande_id;
        this.date_commande = date_commande;
        this.client_id = client_id;
        this.total = total;
    }

    public Long getCommande_id() {
        return commande_id;
    }

    public void setCommande_id(Long commande_id) {
        this.commande_id = commande_id;
    }

    public String getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(String date_commande) {
        this.date_commande = date_commande;
    }

    public Client getClient_id() {
        return client_id;
    }

    public void setClient_id(Client client_id) {
        this.client_id = client_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
