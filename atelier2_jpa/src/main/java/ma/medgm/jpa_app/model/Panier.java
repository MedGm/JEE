package ma.medgm.jpa_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "panier")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LignePanier> lignes = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }

    public void addLigne(LignePanier lignePanier) {
        lignes.add(lignePanier);
        lignePanier.setPanier(this);
    }

    public void removeLigne(LignePanier lignePanier) {
        lignes.remove(lignePanier);
        lignePanier.setPanier(null);
    }

    public void ajouterProduit(Produit produit) {
        for (LignePanier ligne : lignes) {
            if (ligne.getProduit().getId().equals(produit.getId())) {
                ligne.setQuantite(ligne.getQuantite() + 1);
                return;
            }
        }
        LignePanier nouvelleLigne = new LignePanier();
        nouvelleLigne.setProduit(produit);
        nouvelleLigne.setQuantite(1);
        nouvelleLigne.setPanier(this);
        lignes.add(nouvelleLigne);
    }

    public void supprimerProduit(Long produitId) {
        lignes.removeIf(ligne -> ligne.getProduit().getId().equals(produitId));
    }

    public double getTotal() {
        return lignes.stream()
                .mapToDouble(ligne -> ligne.getProduit().getPrix() * ligne.getQuantite())
                .sum();
    }
}
