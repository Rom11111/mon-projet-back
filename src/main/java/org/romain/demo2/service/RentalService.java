package org.romain.demo2.service;

import lombok.RequiredArgsConstructor;
import org.romain.demo2.dao.ProductDao;
import org.romain.demo2.dao.RentalDao;
import org.romain.demo2.dao.UserDao;
import org.romain.demo2.dto.RentalRequestDto;
import org.romain.demo2.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalDao rentalDao;
    private final ProductDao productDao;
    private final UserDao userDao;

    /**
     * Méthode pour créer une nouvelle location.
     * Uniquement un utilisateur de rôle CLIENT peut faire ça.
     * Je vérifie que le produit et le client existent,
     * que les dates sont cohérentes,
     * et qu'il n'y a pas déjà une réservation sur cette période.
     */
    public Rental createRental(RentalRequestDto request, Integer clientId) {
        // 1. Je vérifie que le produit existe
        Product product = productDao.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        // 2. Je vérifie que le client existe
        User client = userDao.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        // 3. Je vérifie que le rôle de l'utilisateur est bien CLIENT
        if (client.getRole() != Role.CLIENT) {
            throw new RuntimeException("Seuls les clients peuvent réserver des produits");
        }

        // 4. Vérification des dates de location
        LocalDate start = request.getStartDate();
        LocalDate end = request.getEndDate();

        if (start.isAfter(end)) {
            throw new RuntimeException("La date de début doit être avant la date de fin");
        }

        // 5. Vérifie si le produit est déjà réservé sur cette période
        // Une location est en conflit si :
        // - une réservation commence avant ou pendant la fin demandée
        // - et se termine après ou pendant le début demandé
        boolean isAvailable = rentalDao
                .findByProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        product.getId(), end, start
                ).isEmpty(); // s’il n’y a rien, alors c’est dispo

        if (!isAvailable) {
            throw new RuntimeException("Le produit est déjà réservé sur cette période");
        }

        // 6. Création de la réservation
        Rental rental = new Rental();
        rental.setProduct(product);
        rental.setClient(client);
        rental.setStartDate(start);
        rental.setEndDate(end);
        rental.setStatus(RentalStatus.PENDING); // statut par défaut
        rental.setCreatedAt(LocalDateTime.now());

        return rentalDao.save(rental);
    }

    /**
     * Méthode pour récupérer toutes les locations visibles par un utilisateur.
     * - Un ADMIN ou un TECH voit tout
     * - Un CLIENT ne voit que ses propres réservations
     */
    public List<Rental> findAllAccessibleBy(User user) {
        if (user.getRole() == Role.CLIENT) {
            return rentalDao.findByClientId(user.getId());
        }
        return rentalDao.findAll();
    }

    /**
     * Permet de récupérer une réservation par ID.
     */
    public Optional<Rental> findById(int id) {
        return rentalDao.findById((long) id);
    }

    /**
     * Vérifie si un utilisateur a accès à une location.
     * - Un CLIENT ne peut accéder qu’à ses propres réservations
     * - Un TECH ou ADMIN peut voir toutes les locations
     */
    public boolean canAccessRental(User user, Rental rental) {
        return user.getRole() != Role.CLIENT || rental.getClient().getId().equals(user.getId());
    }

    /**
     * Met à jour une location.
     * Je vérifie que la réservation existe, que l’utilisateur est autorisé,
     * puis je mets à jour les dates (simple, sans gestion de conflit ici).
     */
    public ResponseEntity<?> updateRental(int rentalId, User currentUser, RentalRequestDto dto) {
        Optional<Rental> optional = rentalDao.findById((long) rentalId);

        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body("Location non trouvée.");
        }

        Rental rental = optional.get();

        // Seuls les TECH ou ADMIN peuvent modifier une réservation
        if (currentUser.getRole() == Role.TECH || currentUser.getRole() == Role.ADMIN) {
            rental.setStartDate(dto.getStartDate());
            rental.setEndDate(dto.getEndDate());
            rental.setStatus(RentalStatus.PENDING); // reset du statut si modifié

            rentalDao.save(rental);
            return ResponseEntity.status(204).build(); // No Content
        }

        return ResponseEntity.status(403).body("Vous n'avez pas les droits pour modifier cette location.");
    }

    /**
     * Supprime une location.
     * - Un ADMIN peut tout supprimer
     * - Un TECH peut supprimer les locations qu’il a créées (ou selon logique métier)
     */
    public ResponseEntity<?> deleteRental(int rentalId, User currentUser) {
        Optional<Rental> optional = rentalDao.findById((long) rentalId);

        if (optional.isEmpty()) {
            return ResponseEntity.status(404).body("Location non trouvée.");
        }

        Rental rental = optional.get();

        // ADMIN peut tout supprimer, TECH seulement ce qu’il a créé
        if (currentUser.getRole() == Role.ADMIN ||
                (currentUser.getRole() == Role.TECH &&
                        rental.getClient().getId().equals(currentUser.getId()))) {

            rentalDao.deleteById(rental.getId());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(403).body("Vous n'avez pas les droits pour supprimer cette location.");
    }
}

//Ce service gère toute la logique liée aux réservations.
//Il vérifie les rôles, la disponibilité du produit, les droits d'accès,
//et applique une politique claire pour les actions selon les profils utilisateurs.
//Le code est structuré, commenté, et maintenable.