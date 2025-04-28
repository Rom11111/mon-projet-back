package org.romain.demo2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false) // On ne peut pas avoir 2 fois le même Email
    protected String email;

    @NotBlank
    @Column(nullable = false)
    protected String password;


    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('BORROWER','TECH', 'ADMIN')")
    protected Role role;
    //protected boolean admin; // si admin est vrai c'est un admin, si faux ce n'est pas un admin
}
