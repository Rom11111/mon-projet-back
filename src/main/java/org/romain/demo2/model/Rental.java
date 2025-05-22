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
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @NotNull
    @ManyToOne(optional = false)
    private Product product;

    @NotNull
    @Column(nullable = false)
    private Double price;

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

