package org.romain.demo2.service;

import lombok.RequiredArgsConstructor;
import org.romain.demo2.dao.ProductDao;
import org.romain.demo2.dao.RentalDao;
import org.romain.demo2.dao.UserDao;
import org.romain.demo2.dto.RentalRequestDto;
import org.romain.demo2.exception.BusinessException;
import org.romain.demo2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // Logger pour suivre ce qui se passe côté serveur
    private static final Logger log = LoggerFactory.getLogger(RentalService.class);

    /**
     * Crée une nouvelle location après toutes les vérifications métier.
     * Je vérifie : produit existant, client valide, dates cohérentes, produit dispo.
     */
    public Rental createRental(RentalRequestDto request, Integer clientId) {
        log.info("Création tentative - clientId={}, produitId={}, période={} → {}",
                clientId, request.getProductId(), request.getStartDate(), request.getEndDate());

        // Je vérifie si le produit demandé existe
        Product product = productDao.findById(request.getProductId())
                .orElseThrow(() -> {
                    log.warn("Produit introuvable : id={}", request.getProductId());
                    return new BusinessException("Produit introuvable");
                });

        // Je vérifie si le client existe
        User client = userDao.findById(clientId)
                .orElseThrow(() -> {
                    log.warn("Client introuvable : id={}", clientId);
                    return new BusinessException("Client introuvable");
                });

        // Je vérifie que l'utilisateur est bien un client
        if (client.getRole() != Role.CLIENT) {
            log.warn("Rôle invalide pour réservation - userId={}, rôle={}", client.getId(), client.getRole());
            throw new BusinessException("Seuls les clients peuvent réserver des produits");
        }

        // Je vérifie la cohérence des dates
        if (request.getStartDate().isAfter(request.getEndDate())) {
            log.warn("Dates incohérentes - start={}, end={}", request.getStartDate(), request.getEndDate());
            throw new BusinessException("La date de début doit être avant la date de fin");
        }

        // Je vérifie si le produit est déjà réservé sur cette période
        boolean isAvailable = rentalDao
                .findByProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        product.getId(), request.getEndDate(), request.getStartDate()
                ).isEmpty();

        if (!isAvailable) {
            log.warn("Conflit de réservation - produitId={}, période={} → {}",
                    product.getId(), request.getStartDate(), request.getEndDate());
            throw new BusinessException("Le produit est déjà réservé sur cette période");
        }

        // Je construis l’objet location
        Rental rental = new Rental();
        rental.setProduct(product);
        rental.setClient(client);
        rental.setStartDate(request.getStartDate());
        rental.setEndDate(request.getEndDate());
        rental.setStatus(RentalStatus.PENDING);
        rental.setCreatedAt(LocalDateTime.now());

        log.info("Location créée avec succès - rentalId temporaire=nouveau, clientId={}, produitId={}",
                clientId, product.getId());

        return rentalDao.save(rental);
    }

    /**
     * Renvoie toutes les locations visibles par un utilisateur selon son rôle.
     * - CLIENT : uniquement ses locations
     * - ADMIN / TECH : toutes
     */
    public List<Rental> findAllAccessibleBy(User user) {
        log.info("Liste des locations récupérée pour userId={}, rôle={}", user.getId(), user.getRole());
        if (user.getRole() == Role.CLIENT) {
            return rentalDao.findByClientId(user.getId());
        }
        return rentalDao.findAll();
    }

    /**
     * Permet de récupérer une location par son ID.
     */
    public Optional<Rental> findById(int id) {
        log.debug("Recherche location par ID - rentalId={}", id);
        return rentalDao.findById((long) id);
    }

    /**
     * Vérifie si l'utilisateur connecté a le droit d'accéder à une location.
     * Un client ne peut voir que ses propres locations.
     */
    public boolean canAccessRental(User user, Rental rental) {
        log.debug("Vérification accès location - userId={}, rentalId={}, résultat={}",
                user.getId(), rental.getId(), user.getRole() != Role.CLIENT || rental.getClient().getId().equals(user.getId()));
        return user.getRole() != Role.CLIENT || rental.getClient().getId().equals(user.getId());
    }

    /**
     * Met à jour une location si l'utilisateur y est autorisé.
     * Retourne un Optional avec la location mise à jour, ou vide sinon.
     */
    public Optional<Rental> updateRentalWithResult(int rentalId, User currentUser, RentalRequestDto dto) {
        log.info("Mise à jour demandée - rentalId={}, userId={}, rôle={}, période={} → {}",
                rentalId, currentUser.getId(), currentUser.getRole(), dto.getStartDate(), dto.getEndDate());

        Optional<Rental> optional = rentalDao.findById((long) rentalId);

        if (optional.isEmpty()) {
            log.warn("Location non trouvée - rentalId={}", rentalId);
            return Optional.empty();
        }

        Rental rental = optional.get();

        if (currentUser.getRole() == Role.TECH || currentUser.getRole() == Role.ADMIN) {
            rental.setStartDate(dto.getStartDate());
            rental.setEndDate(dto.getEndDate());
            rental.setStatus(RentalStatus.PENDING);

            rentalDao.save(rental);
            log.info("Location mise à jour avec succès - rentalId={}", rentalId);
            return Optional.of(rental);
        }

        log.warn("Mise à jour refusée - userId={} n'a pas les droits pour rentalId={}", currentUser.getId(), rentalId);
        return Optional.empty();
    }

    /**
     * Supprime une location si l'utilisateur y est autorisé.
     * Retourne true si suppression faite, false sinon.
     */
    public boolean deleteRental(int rentalId, User currentUser) {
        log.info("Suppression demandée - rentalId={}, userId={}, rôle={}", rentalId, currentUser.getId(), currentUser.getRole());

        Optional<Rental> optional = rentalDao.findById((long) rentalId);

        if (optional.isEmpty()) {
            log.warn("Location à supprimer non trouvée - rentalId={}", rentalId);
            return false;
        }

        Rental rental = optional.get();

        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isOwnerTech = currentUser.getRole() == Role.TECH &&
                rental.getClient().getId().equals(currentUser.getId());

        if (isAdmin || isOwnerTech) {
            rentalDao.deleteById(rental.getId());
            log.info("Location supprimée - rentalId={}, supprimée par userId={}", rental.getId(), currentUser.getId());
            return true;
        }

        log.warn("Suppression refusée - userId={} n’a pas le droit de supprimer rentalId={}", currentUser.getId(), rentalId);
        return false;
    }
}
