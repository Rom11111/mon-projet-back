package org.romain.demo2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.romain.demo2.view.ProductViews;


@Getter
@Setter
@Entity
public class User {
    public interface RegistrationGroup { }
    public interface UpdateGroup {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(groups = UpdateGroup.class)
    @Column(nullable = false)
    private String firstname;

    @NotBlank(groups = UpdateGroup.class)
    @Column(nullable = false)
    private String lastname;

    @NotBlank(groups = RegistrationGroup.class)
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @NotBlank(groups = RegistrationGroup.class)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('CLIENT','TECH', 'ADMIN')")
    private Role role;

    @Column
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED')")
    private UserStatus userStatus = UserStatus.ACTIVE;

    @CreationTimestamp
    @Column(updatable = false)
    private java.util.Date createdAt;

    @UpdateTimestamp
    private java.util.Date updatedAt;

    @NotBlank(groups = UpdateGroup.class)
    @Column(nullable = false)
    private String company;

    @NotBlank(groups = UpdateGroup.class)
    @Column(nullable = false)
    private String companyAddress;

    @NotBlank(groups = UpdateGroup.class)
    @Column(nullable = false)
    private String phone;

    private String emailVerificationToken;
}
