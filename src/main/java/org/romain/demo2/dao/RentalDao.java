package org.romain.demo2.dao;

import org.romain.demo2.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface qui gère tout ce qui touche aux locations (Rental) en base.
 * Ici on utilise JpaRepository, donc on a déjà tous les trucs CRUD classiques.
 */
public interface RentalDao extends JpaRepository<Rental, Integer> {

    /**
     * Permet de récupérer toutes les réservations faites par un user.
     * Utile pour afficher son historique, ou filtrer ce qu’il peut voir.
     *
     * @param userId l'ID de l'utilisateur connecté
     * @return les réservations liées à ce user
     */
    List<Rental> findByUserId(Integer userId);

    /**
     * ⚠️ Méthode super importante : elle check si un produit est déjà réservé à une date.
     * Si cette requête retourne une liste NON vide → le produit est déjà pris ce jour-là.
     * Tu peux t'en servir pour bloquer une nouvelle réservation côté service.
     *
     * @param productId l'ID du produit qu’on veut réserver
     * @param date      la date visée
     * @return la liste des rentals existants à cette date pour ce produit (normalement vide si tout est OK)
     */
    @Query("SELECT r FROM Rental r WHERE r.product.id = :productId AND r.date = :date")
    List<Rental> findByProductAndDate(@Param("productId") Integer productId,
                                      @Param("date") LocalDate date);
}
