package org.romain.demo2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Représente une location d'un produit par un client sur une période donnée.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rental {

    // Identifiant unique généré automatiquement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Produit concerné par la location
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Client qui réserve (doit avoir le rôle CLIENT)
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private User client;


    // Date de début de la location
    private LocalDate startDate;

    // Date de fin de la location
    private LocalDate endDate;

    // Statut de la location (PENDING, APPROVED, etc.)
    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    // Date de création de la location
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull(message = "La quantité est requise")
    @Min(value = 1, message = "La quantité doit être au moins 1")
    private Integer quantity;
}


//J’utilise Long pour l’ID car c’est adapté aux bases de données (équivalent à BIGINT), et nullable
//tant que l’objet n’est pas encore enregistré. Le modèle est simple : une location est liée à un produit,
//à un client, et à une période. Le statut permet de suivre l’évolution.