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
     * Retourne les réservations qui chevauchent une période donnée pour un produit.
     * Une réservation est en conflit si :
     * - Sa date de début est avant ou le jour de la fin demandée
     * - Sa date de fin est après ou le jour du début demandé
     * Si cette méthode retourne une liste non vide, alors le produit est déjà réservé.
     */
    List<Rental> findByProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long productId, LocalDate end, LocalDate start
    );

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
