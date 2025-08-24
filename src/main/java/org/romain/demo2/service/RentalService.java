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

import org.romain.demo2.dao.ReportDao;
import org.romain.demo2.model.ReportStatus;
import org.romain.demo2.model.Report;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalDao rentalDao;
    private final ProductDao productDao;
    private final UserDao userDao;
    private final ReportDao reportDao;

    // Logger pour suivre ce qui se passe côté serveur
    private static final Logger log = LoggerFactory.getLogger(RentalService.class);

    /**
     * Création d’une nouvelle location.
     * Vérifie : produit existant, client valide, dates correctes, quantité dispo.
     */
    public Rental createRental(RentalRequestDto request, Long clientId) {
        log.info("Création tentative - clientId={}, produitId={}, période={} → {}",
                clientId, request.getProductId(), request.getStartDate(), request.getEndDate());

        // Vérifie si le produit demandé existe
        Product product = productDao.findById(request.getProductId())
                .orElseThrow(() -> {
                    log.warn("Produit introuvable : id={}", request.getProductId());
                    return new BusinessException("Produit introuvable");
                });

        // Vérifie si le client existe
        User client = userDao.findById(clientId)
                .orElseThrow(() -> {
                    log.warn("Client introuvable : id={}", clientId);
                    return new BusinessException("Client introuvable");
                });

        // Vérifie que c’est bien un CLIENT
        if (client.getRole() != Role.CLIENT) {
            log.warn("Rôle invalide pour réservation - userId={}, rôle={}", client.getId(), client.getRole());
            throw new BusinessException("Seuls les clients peuvent réserver des produits");
        }

        // Vérifie la quantité demandée
        if (request.getQuantity() == null || request.getQuantity() < 1) {
            log.warn("Quantité invalide - valeur={}", request.getQuantity());
            throw new BusinessException("La quantité doit être au moins de 1.");
        }

        // Vérifie la cohérence des dates
        if (request.getStartDate().isAfter(request.getEndDate())) {
            log.warn("Dates incohérentes - start={}, end={}", request.getStartDate(), request.getEndDate());
            throw new BusinessException("La date de début doit être avant la date de fin");
        }

        // Vérifie le stock disponible sur la période
        int reserved = rentalDao.sumQuantityForProductBetweenDates(
                product.getId(), request.getStartDate(), request.getEndDate()
        );

        // Stock restant = stock total - déjà réservé
        int available = product.getStock() - reserved;

        // Si la demande dépasse le stock restant → on bloque
        if (request.getQuantity() > available) {
            throw new BusinessException(
                    "Stock insuffisant : " + available + " unité(s) disponibles sur cette période."
            );
        }

        // Création de l’objet location
        Rental rental = new Rental();
        rental.setProduct(product);
        rental.setClient(client);
        rental.setStartDate(request.getStartDate());
        rental.setEndDate(request.getEndDate());
        rental.setStatus(RentalStatus.PENDING);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setQuantity(request.getQuantity());

        log.info("Location créée avec succès - rentalId temporaire=nouveau, clientId={}, produitId={}",
                clientId, product.getId());

        return rentalDao.save(rental);
    }

    /**
     * Retourne les locations accessibles selon le rôle :
     * - CLIENT → seulement ses locations
     * - ADMIN / TECH → toutes
     */
    public List<Rental> findAllAccessibleBy(User user) {
        log.info("Liste des locations récupérée pour userId={}, rôle={}", user.getId(), user.getRole());
        if (user.getRole() == Role.CLIENT) {
            return rentalDao.findByClientId(user.getId());
        }
        return rentalDao.findAll();
    }

    /**
     * Cherche une location par son ID.
     */
    public Optional<Rental> findById(Long id) {
        log.debug("Recherche location par ID - rentalId={}", id);
        return rentalDao.findById(id);
    }

    /**
     * Vérifie si l’utilisateur a le droit de voir une location.
     * Un client → seulement les siennes.
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
    public Optional<Rental> updateRentalWithResult(Long rentalId, User currentUser, RentalRequestDto dto) {
        log.info("Mise à jour demandée - rentalId={}, userId={}, rôle={}, période={} → {}",
                rentalId, currentUser.getId(), currentUser.getRole(), dto.getStartDate(), dto.getEndDate());

        Optional<Rental> optional = rentalDao.findById(rentalId);

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
     * Change seulement le statut d’une location.
     * (APPROVED, REJECTED… réservé ADMIN/TECH)
     */
    public Rental updateStatus(Long rentalId, RentalStatus newStatus) {
        // Je cherche la location par son ID, sinon je lance une erreur métier claire
        Rental rental = rentalDao.findById(rentalId)
                .orElseThrow(() -> new BusinessException("Location introuvable"));

        // Je mets à jour uniquement le statut
        rental.setStatus(newStatus);

        // J'enregistre la modification en base et je retourne l'objet mis à jour
        return rentalDao.save(rental);
    }

    /**
     * Supprime une location si autorisé.
     * - ADMIN → tout supprimer
     * - TECH → peut supprimer seulement ses propres locations
     */
    public boolean deleteRental(Long rentalId, User currentUser) {
        log.info("Suppression demandée - rentalId={}, userId={}, rôle={}", rentalId, currentUser.getId(), currentUser.getRole());

        Optional<Rental> optional = rentalDao.findById(rentalId);

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

    /**
     * Création d’un signalement par un client sur une de ses locations.
     */
    public Report reportProduct(Long rentalId, Long clientId, String description) {
        log.info("Signalement demandé - rentalId={}, clientId={}", rentalId, clientId);

        Rental rental = rentalDao.findById(rentalId)
                .orElseThrow(() -> {
                    log.warn("Location introuvable - rentalId={}", rentalId);
                    return new BusinessException("Location introuvable");
                });

        User client = userDao.findById(clientId)
                .orElseThrow(() -> {
                    log.warn("Client introuvable - id={}", clientId);
                    return new BusinessException("Client introuvable");
                });

        if (!rental.getClient().getId().equals(clientId)) {
            log.warn("Signalement refusé - userId={} n'est pas le propriétaire de rentalId={}", clientId, rentalId);
            throw new BusinessException("Vous ne pouvez signaler qu'une de vos propres locations");
        }

        Report report = Report.builder()
                .rental(rental)
                .reportedBy(client)
                .description(description)
                .status(ReportStatus.OPEN)
                .build();

        reportDao.save(report);

        log.info("Signalement enregistré - reportId={}, rentalId={}, clientId={}",
                report.getId(), rentalId, clientId);

        return report;
    }
}
