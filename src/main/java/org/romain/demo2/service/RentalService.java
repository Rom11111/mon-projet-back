package org.romain.demo2.service;

import org.romain.demo2.dao.RentalDao;
import org.romain.demo2.model.Product;
import org.romain.demo2.model.Rental;
import org.romain.demo2.model.User;
import org.romain.demo2.dto.RentalDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalDao rentalDao;

    // Injection du DAO via constructeur
    public RentalService(RentalDao rentalDao) {
        this.rentalDao = rentalDao;
    }

    /**
     * Vérifie si un produit est disponible pour une date donnée.
     * Si une location existe déjà pour cette date, le produit est considéré comme réservé.
     */
    public boolean isProductAvailable(Integer productId, LocalDate date) {
        List<Rental> rentals = rentalDao.findByProductAndDate(productId, date);
        return rentals.isEmpty(); // Si aucune réservation ce jour-là, c’est dispo
    }

    /**
     * Crée une nouvelle location si le produit est dispo pour la date donnée.
     */
    public Rental createRentalIfAvailable(Rental rental) {
        if (!isProductAvailable(rental.getProduct().getId(), rental.getDate())) {
            throw new IllegalStateException("Produit déjà réservé à cette date.");
        }

        return rentalDao.save(rental);
    }

    /**
     * Récupère une location par ID.
     */
    public Optional<Rental> findById(int id) {
        return rentalDao.findById(id);
    }

    /**
     * Vérifie si un utilisateur a le droit d’accéder à une location donnée :
     * - ADMIN ou TECH → OK
     * - CLIENT → uniquement si c’est sa propre location
     */
    public boolean canAccessRental(User user, Rental rental) {
        return user.isAdmin() || user.isTech() || rental.getUser().getId().equals(user.getId());
    }

    /**
     * Retourne la liste des locations visibles par un utilisateur donné :
     * - ADMIN/TECH → toutes
     * - CLIENT → uniquement les siennes
     */
    public List<Rental> findAllAccessibleBy(User user) {
        if (user.isAdmin() || user.isTech()) {
            return rentalDao.findAll();
        } else {
            return rentalDao.findByUserId(user.getId());
        }
    }

    /**
     * Crée une location à partir d’un DTO, en vérifiant la disponibilité du produit.
     */
    public Rental createRental(User user, RentalDTO dto) {
        if (!isProductAvailable(dto.getProductId(), dto.getDate())) {
            throw new IllegalStateException("Produit déjà réservé à cette date.");
        }

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setProduct(new Product(dto.getProductId())); // instanciation minimale
        rental.setDate(dto.getDate());
        rental.setPrice(dto.getPrice());
        rental.setReservationDate(java.time.LocalDateTime.now());
        rental.setComments(dto.getComments());
        rental.setConfirmed(false);

        return rentalDao.save(rental);
    }

    /**
     * Met à jour une location si l'utilisateur en a le droit.
     */
    public ResponseEntity<?> updateRental(int id, User user, RentalDTO dto) {
        Optional<Rental> optional = rentalDao.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Rental existing = optional.get();
        if (!canAccessRental(user, existing)) {
            return ResponseEntity.status(403).body("Vous n'avez pas le droit de modifier cette location.");
        }

        existing.setDate(dto.getDate());
        existing.setPrice(dto.getPrice());
        existing.setComments(dto.getComments());

        rentalDao.save(existing);
        return ResponseEntity.noContent().build(); // 204 = pas de contenu mais OK
    }

    /**
     * Supprime une location si l'utilisateur en a le droit.
     */
    public ResponseEntity<?> deleteRental(int id, User user) {
        Optional<Rental> optional = rentalDao.findById(id);
        if (optional.isEmpty()) return ResponseEntity.notFound().build();

        Rental rental = optional.get();
        if (!canAccessRental(user, rental)) {
            return ResponseEntity.status(403).body("Vous n'avez pas le droit de supprimer cette location.");
        }

        rentalDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
