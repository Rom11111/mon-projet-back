package org.romain.demo2.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Représente une location de produit dans le système.
 */
@Getter
@Setter
@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Date effective de la location.
     */
    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    @NotNull
    @ManyToOne(optional = false)
    private Product product;


    @NotNull
    @Column(nullable = false)
    private Double price;

    @NotNull
    @ManyToOne(optional = false)
    private User user;

    /**
     * Date et heure à laquelle la réservation a été créée.
     */
    @NotNull
    @Column(nullable = false)
    private LocalDateTime reservationDate;

    /**
     * Date et heure d'expiration de la location.
     */
    @Column
    private LocalDateTime expirationDate;

    /**
     * Indique si la location a été confirmée.
     */
    @Column
    private boolean confirmed = false;

    // Méthodes utilitaires

    /**
     * Vérifie si la location est active (non expirée).
     * @return true si la location est toujours active
     */
    @Transient
    public boolean isActive() {
        if (expirationDate == null) {
            return true;
        }
        return expirationDate.isAfter(LocalDateTime.now());
    }
}