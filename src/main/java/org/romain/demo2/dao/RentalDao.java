package org.romain.demo2.dao;

import org.romain.demo2.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RentalDao extends JpaRepository<Rental, Integer> {

    /**
     * Récupère toutes les locations liées à un utilisateur (client).
     */
    List<Rental> findByUserId(Integer userId);

    /**
     * Récupère toutes les locations d’un produit à une date donnée.
     * Utilisé pour vérifier si un produit est déjà réservé ce jour-là.
     */
    @Query("SELECT r FROM Rental r WHERE r.product.id = :productId AND r.date = :date")
    List<Rental> findByProductAndDate(@Param("productId") Integer productId,
                                      @Param("date") LocalDate date);
}

