package org.romain.demo2.service;

import org.romain.demo2.dao.RentalDao;
import org.romain.demo2.model.Rental;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

    private final RentalDao rentalDao;

    // Injection du DAO
    public RentalService(RentalDao rentalDao) {
        this.rentalDao = rentalDao;
    }

    // Vérifie si le produit est libre à la date donnée
    public boolean isProductAvailable(Integer productId, LocalDate date) {
        List<Rental> rentals = rentalDao.findByProductAndDate(productId, date);
        return rentals.isEmpty();
    }

    // Crée une réservation si le produit est dispo
    public Rental createRentalIfAvailable(Rental rental) {
        if (!isProductAvailable(rental.getProduct().getId(), rental.getDate())) {
            throw new IllegalStateException("Produit déjà réservé à cette date.");
        }
        return rentalDao.save(rental);
    }
}
