package org.romain.demo2.model;

public enum RentalStatus {
    PENDING,     // En attente de validation
    APPROVED,    // Acceptée
    REJECTED,    // Refusée
    CANCELED     // Annulée par le client
}

//J’ai défini des statuts simples mais clairs pour refléter les étapes classiques d’une réservation.
//On peut facilement les exploiter dans le back ou les afficher sur le front.