package org.romain.demo2.controller;

import jakarta.validation.Valid;
import org.romain.demo2.dao.RentalDao;
import org.romain.demo2.model.Rental;
import org.romain.demo2.security.AppUserDetails;
import org.romain.demo2.security.ISecurityUtils;
import org.romain.demo2.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/rentals")
// Accessible à tous les rôles pour respecter les règles d'accès spécifiques dans chaque méthode
@PreAuthorize("hasAnyRole('CLIENT', 'TECH', 'ADMIN')")
public class RentalController {

    private final RentalDao rentalDao;
    private final ISecurityUtils securityUtils;
    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalDao rentalDao, ISecurityUtils securityUtils, RentalService rentalService) {
        this.rentalDao = rentalDao;
        this.securityUtils = securityUtils;
        this.rentalService = rentalService;
    }

    @GetMapping
    public List<Rental> getAll(@AuthenticationPrincipal AppUserDetails userDetails) {
        String role = securityUtils.getRole(userDetails);
        if (role.equals("ROLE_ADMIN") || role.equals("ROLE_TECH")) {
            return rentalDao.findAll(); // ADMIN et TECH voient tout
        } else {
            return rentalDao.findByUserId(userDetails.getUser().getId()); // CLIENT voit ses réservations
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getById(@PathVariable int id,
                                          @AuthenticationPrincipal AppUserDetails userDetails) {
        Optional<Rental> optionalRental = rentalDao.findById(id);
        if (optionalRental.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Rental rental = optionalRental.get();
        String role = securityUtils.getRole(userDetails);
        boolean isOwner = rental.getUser().getId().equals(userDetails.getUser().getId());

        if (!role.equals("ROLE_ADMIN") && !role.equals("ROLE_TECH") && !isOwner) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(rental, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Rental rental,
                                    @AuthenticationPrincipal AppUserDetails userDetails) {
        rental.setId(null);
        rental.setUser(userDetails.getUser());

        try {
            Rental saved = rentalService.createRentalIfAvailable(rental);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id,
                                       @AuthenticationPrincipal AppUserDetails userDetails) {
        Optional<Rental> optional = rentalDao.findById(id);
        if (optional.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Rental rental = optional.get();
        String role = securityUtils.getRole(userDetails);
        boolean isOwner = rental.getUser().getId().equals(userDetails.getUser().getId());

        // Seuls ADMIN et TECH peuvent supprimer — TECH seulement ses propres locs
        if (role.equals("ROLE_CLIENT") || (!role.equals("ROLE_ADMIN") && !isOwner)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        rentalDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id,
                                       @RequestBody @Valid Rental updatedRental,
                                       @AuthenticationPrincipal AppUserDetails userDetails) {
        Optional<Rental> optional = rentalDao.findById(id);
        if (optional.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Rental existing = optional.get();
        String role = securityUtils.getRole(userDetails);
        boolean isOwner = existing.getUser().getId().equals(userDetails.getUser().getId());

        // Seuls ADMIN et TECH peuvent modifier — TECH seulement ses propres locs
        if (role.equals("ROLE_CLIENT") || (!role.equals("ROLE_ADMIN") && !isOwner)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        updatedRental.setId(id);
        updatedRental.setUser(existing.getUser());
        rentalDao.save(updatedRental);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
