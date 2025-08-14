package org.romain.demo2.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Représente un signalement (report) fait par un CLIENT sur une location (Rental).
 */
@Entity
@Table(name = "reports")
@Getter @Setter // Lombok génère automatiquement les getters et setters
@NoArgsConstructor // Génère un constructeur vide
@AllArgsConstructor // Génère un constructeur avec tous les champs
@Builder // Permet de créer un objet avec Report.builder()...
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // identifiant du signalement

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    private Rental rental; // location concernée par le problème

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_by_id")
    private User reportedBy; // utilisateur qui a signalé (CLIENT)

    @Column(nullable = false, length = 2000)
    private String description; // explication du problème par le client

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ReportStatus status = ReportStatus.OPEN; // état du signalement (ouvert/en cours/résolu)

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(); // date de création du signalement

    private LocalDateTime resolvedAt; // date de résolution
}
