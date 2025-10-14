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
@Table(name = "vitrine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vitrine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @OneToMany
    @JoinColumn(name = "vitrine_id")
    private List<Produit> produits = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (nom == null || nom.isEmpty()) {
            nom = "Vitrine #" + System.currentTimeMillis();
        }
    }
}
