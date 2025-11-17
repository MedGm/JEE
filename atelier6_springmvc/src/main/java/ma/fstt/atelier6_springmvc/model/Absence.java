package ma.fstt.atelier6_springmvc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "absences",
       indexes = {
           @Index(columnList = "dateAbsence"),
           @Index(columnList = "etudiant_id")
       })
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @NotNull(message = "La date d'absence est obligatoire")
    @Column(nullable = false)
    private LocalDate dateAbsence;

    @Size(max = 255, message = "Le motif ne peut pas dépasser 255 caractères")
    private String motif;

    private boolean justifie = false;

    private String remarque;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public Absence() {
    }

    public Absence(Etudiant etudiant, LocalDate dateAbsence, String motif, boolean justifie, String remarque) {
        this.etudiant = etudiant;
        this.dateAbsence = dateAbsence;
        this.motif = motif;
        this.justifie = justifie;
        this.remarque = remarque;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public LocalDate getDateAbsence() {
        return dateAbsence;
    }

    public void setDateAbsence(LocalDate dateAbsence) {
        this.dateAbsence = dateAbsence;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public boolean isJustifie() {
        return justifie;
    }

    public void setJustifie(boolean justifie) {
        this.justifie = justifie;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Absence{" +
                "id=" + id +
                ", dateAbsence=" + dateAbsence +
                ", motif='" + motif + '\'' +
                ", justifie=" + justifie +
                '}';
    }
}

