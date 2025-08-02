package org.romain.demo2.dto;

import org.romain.demo2.model.Rental;
import org.romain.demo2.model.RentalStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Ce DTO représente une location simplifiée à envoyer au frontend (Angular)
public record RentalResponseDto(
        Long id,                         // ID de la location
        String productName,             // Nom du produit loué
        LocalDate startDate,            // Date de début de la location
        LocalDate endDate,              // Date de fin de la location
        RentalStatus status,            // Statut de la location (PENDING, APPROVED, etc.)
        LocalDateTime createdAt         // Date de création de la réservation
) {

    // Méthode utilitaire pour transformer une entité Rental en RentalDto
    public static RentalResponseDto from(Rental rental) {
        return new RentalResponseDto(
                rental.getId(),                         // Récupère l’ID
                rental.getProduct().getName(),          // Nom du produit associé
                rental.getStartDate(),                  // Date de début
                rental.getEndDate(),                    // Date de fin
                rental.getStatus(),                     // Statut
                rental.getCreatedAt()                   // Date de création
        );
    }
}

