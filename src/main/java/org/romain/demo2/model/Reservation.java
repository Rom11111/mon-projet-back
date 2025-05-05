package org.romain.demo2.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @NotNull
    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne
    private User user;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime reservationDate;

    @Column
    private LocalDateTime expirationDate;

    @Column
    private boolean confirmed = false;
}

