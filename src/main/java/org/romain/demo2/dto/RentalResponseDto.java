package org.romain.demo2.dto;

import org.romain.demo2.model.Rental;
import org.romain.demo2.model.RentalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record RentalResponseDto(
        Long id,                // ID de la location
        Long productId,         // ID du produit loué
        Long clientId,          // ID du client
        String productName,     // Nom du produit
        String clientFirstname, // Prénom du client
        String clientLastname,  // Nom du client
        LocalDate startDate,    // Date de début
        LocalDate endDate,      // Date de fin
        RentalStatus status,    // Statut de la location
        LocalDateTime reservationDate, // Date de réservation
        Integer quantity,       // Quantité louée
        BigDecimal price,       // Prix unitaire du produit
        BigDecimal total        // Prix total = prix * quantité * jours
) {
    public static RentalResponseDto from(Rental rental) {
        // Récupération du prix depuis le produit
        BigDecimal price = rental.getProduct().getPrice();

        // Calcul du nombre de jours entre début et fin
        long days = ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate());
        if (days <= 0) days = 1; // On facture minimum 1 jour

        // Calcul du prix total : prix unitaire × quantité × jours
        BigDecimal total = price
                .multiply(BigDecimal.valueOf(rental.getQuantity()))
                .multiply(BigDecimal.valueOf(days));

        // Création du DTO
        return new RentalResponseDto(
                rental.getId(),
                rental.getProduct().getId(),
                rental.getClient().getId(),
                rental.getProduct().getName(),
                rental.getClient().getFirstname(),
                rental.getClient().getLastname(),
                rental.getStartDate(),
                rental.getEndDate(),
                rental.getStatus(),
                rental.getCreatedAt(),
                rental.getQuantity(),
                price,
                total
        );
    }
}
