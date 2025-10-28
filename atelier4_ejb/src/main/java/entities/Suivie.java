package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "suivie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Suivie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suivie")
    private Long idSuivie;

    @Column(name = "note", nullable = false)
    private Double note;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    // Many Suivie belong to one Etudiant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_etudiant", nullable = false)
    private Etudiant etudiant;

    // Many Suivie belong to one Module
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_module", nullable = false)
    private Module module;
}
