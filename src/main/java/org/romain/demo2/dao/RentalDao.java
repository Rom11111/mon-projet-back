package org.romain.demo2.dao;

import org.romain.demo2.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalDao extends JpaRepository<Rental, Integer> {
    List<Rental> findByUserId(Integer userId);
}
