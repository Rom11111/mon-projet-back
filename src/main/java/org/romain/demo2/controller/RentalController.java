package org.romain.demo2.controller;

import jakarta.validation.Valid;
import org.romain.demo2.dao.RentalDao;
import org.romain.demo2.model.Rental;
import org.romain.demo2.security.AppUserDetails;
import org.romain.demo2.security.ISecurityUtils;
import org.romain.demo2.security.IsClient;
import org.romain.demo2.security.IsTech;
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
// restreint l'accès à l'ensemble du contrôleur
@IsTech
public class RentalController {

    private final RentalDao rentalDao;
    private final ISecurityUtils securityUtils;

    @Autowired
    public RentalController(RentalDao rentalDao, ISecurityUtils securityUtils) {
        this.rentalDao = rentalDao;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    public List<Rental> getAll(@AuthenticationPrincipal AppUserDetails userDetails) {
        String role = securityUtils.getRole(userDetails);

        if (role.equals("ROLE_ADMIN")) {
            return rentalDao.findAll();
        } else {
            return rentalDao.findByUserId(userDetails.getUser().getId());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getById(
            @PathVariable int id,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        Optional<Rental> optionalRental = rentalDao.findById(id);
        if (optionalRental.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Rental rental = optionalRental.get();

        if (!securityUtils.getRole(userDetails).equals("ROLE_ADMIN") &&
                !rental.getUser().getId().equals(userDetails.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(rental, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Rental> create(
            @RequestBody @Valid Rental rental,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        rental.setId(null);
        rental.setUser(userDetails.getUser());
        Rental saved = rentalDao.save(rental);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        Optional<Rental> optional = rentalDao.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Rental rental = optional.get();
        String role = securityUtils.getRole(userDetails);

        if (!role.equals("ROLE_ADMIN") &&
                !rental.getUser().getId().equals(userDetails.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        rentalDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody @Valid Rental updatedRental,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        Optional<Rental> optional = rentalDao.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Rental existing = optional.get();
        String role = securityUtils.getRole(userDetails);

        if (!role.equals("ROLE_ADMIN") &&
                !existing.getUser().getId().equals(userDetails.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        updatedRental.setId(id);
        updatedRental.setUser(existing.getUser()); // conserver le user original
        rentalDao.save(updatedRental);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
