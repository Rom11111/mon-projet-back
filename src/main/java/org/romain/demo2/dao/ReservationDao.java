package org.romain.demo2.dao;

import org.romain.demo2.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationDao extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByUserId(Integer userId);
}
