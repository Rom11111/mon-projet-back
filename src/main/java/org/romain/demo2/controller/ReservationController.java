package org.romain.demo2.controller;

import jakarta.validation.Valid;
import org.romain.demo2.dao.ReservationDao;
import org.romain.demo2.model.Reservation;
import org.romain.demo2.security.AppUserDetails;
import org.romain.demo2.security.ISecurityUtils;
import org.romain.demo2.security.IsAdmin;
import org.romain.demo2.security.IsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;
    private final ISecurityUtils securityUtils;

    @Autowired
    public ReservationController(ReservationDao reservationDao, ISecurityUtils securityUtils) {
        this.reservationDao = reservationDao;
        this.securityUtils = securityUtils;
    }

    @GetMapping
    @IsClient
    public List<Reservation> getAll(@AuthenticationPrincipal AppUserDetails userDetails) {
        String role = securityUtils.getRole(userDetails);

        if (role.equals("ROLE_ADMIN")) {
            return reservationDao.findAll();
        } else {
            return reservationDao.findByUserId(userDetails.getUser().getId());
        }
    }

    @GetMapping("/{id}")
    @IsClient
    public ResponseEntity<Reservation> getById(
            @PathVariable int id,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        Optional<Reservation> optionalReservation = reservationDao.findById(id);
        if (optionalReservation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Reservation reservation = optionalReservation.get();

        if (!securityUtils.getRole(userDetails).equals("ROLE_ADMIN") &&
                !reservation.getUser().getId().equals(userDetails.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PostMapping
    @IsClient
    public ResponseEntity<Reservation> create(
            @RequestBody @Valid Reservation reservation,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        reservation.setId(null);
        reservation.setUser(userDetails.getUser());
        Reservation saved = reservationDao.save(reservation);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @IsClient
    public ResponseEntity<Void> delete(
            @PathVariable int id,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        Optional<Reservation> optional = reservationDao.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Reservation reservation = optional.get();
        String role = securityUtils.getRole(userDetails);

        if (!role.equals("ROLE_ADMIN") &&
                !reservation.getUser().getId().equals(userDetails.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        reservationDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @IsClient
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody @Valid Reservation updatedReservation,
            @AuthenticationPrincipal AppUserDetails userDetails) {

        Optional<Reservation> optional = reservationDao.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Reservation existing = optional.get();
        String role = securityUtils.getRole(userDetails);

        if (!role.equals("ROLE_ADMIN") &&
                !existing.getUser().getId().equals(userDetails.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        updatedReservation.setId(id);
        updatedReservation.setUser(existing.getUser()); // conserver le user original
        reservationDao.save(updatedReservation);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
