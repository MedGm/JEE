package ma.fstt.entities;

public class Client {

    private Long client_id;
    private String nom;
    private String email;
    private String adresse;

    public Client() {
    }

    public Client(Long client_id, String nom, String email, String adresse) {
        this.client_id = client_id;
        this.nom = nom;
        this.email = email;
        this.adresse = adresse;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}
