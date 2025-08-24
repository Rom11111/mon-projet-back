package org.romain.demo2.dao;

import org.romain.demo2.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalDao extends JpaRepository<Rental, Long> {

    /**
     * Retourne toutes les locations faites par un utilisateur donné (client).
     */
    List<Rental> findByClientId(Long clientId);

    /**
     * Cherche toutes les réservations d’un produit qui se chevauchent avec une période donnée.
     * En clair : si la liste n’est pas vide, ça veut dire que le produit est déjà pris sur cette période.
     */
    List<Rental> findByProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long productId, LocalDate end, LocalDate start
    );

    // Calcule combien d’unités d’un produit sont déjà réservées sur une période.
    // Sert à gérer le stock restant.
    @Query("""
        SELECT COALESCE(SUM(r.quantity), 0)
        FROM Rental r
        WHERE r.product.id = :productId
          AND r.startDate <= :endDate
          AND r.endDate >= :startDate
    """)
    int sumQuantityForProductBetweenDates(
            @Param("productId") Long productId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
