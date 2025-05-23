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

    // voir page
    public interface RegistrationGroup {

    }
    public interface UpdateGroup {

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;


    @NotBlank( groups = RegistrationGroup.class)
    @Column(nullable = false)
    protected String firstname;

    @NotBlank( groups = RegistrationGroup.class)
    @Column(nullable = false)
    protected String lastname;

    @NotBlank( groups = RegistrationGroup.class)
    @Column(nullable = false)
    protected String address;

    @NotBlank( groups = RegistrationGroup.class)
    @Column(nullable = false)
    protected String phone;

    @NotBlank( groups = RegistrationGroup.class)
    @Email
    @Column(unique = true, nullable = false) // On ne peut pas avoir 2 fois le même Email
    protected String email;

    @NotBlank( groups = RegistrationGroup.class) // Accepte
    @Column(nullable = false)
    protected String password;


    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('CLIENT','TECH', 'ADMIN')")
    protected Role role;
    //protected boolean admin; // si admin est vrai c'est un admin, si faux ce n'est pas un admin

    protected String emailVerificationToken;
}
