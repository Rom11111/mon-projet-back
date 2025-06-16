package org.romain.demo2.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.romain.demo2.view.ProductDisplayForClient;

@Getter
@Setter
@Entity
public class User {
    public interface RegistrationGroup { }
    public interface UpdateGroup {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotBlank(groups = UpdateGroup.class)
    @Column(nullable = false)
    protected String firstname;

    @NotBlank(groups = UpdateGroup.class)
    @Column(nullable = false)
    @JsonView({ProductDisplayForClient.class})
    protected String lastname;

    @NotBlank(groups = RegistrationGroup.class)
    @Email
    @Column(unique = true, nullable = false)
    protected String email;

    @NotBlank(groups = RegistrationGroup.class)
    @Column(nullable = false)
    protected String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('CLIENT','TECH', 'ADMIN')")
    protected Role role;

    @Column
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus = UserStatus.Active;

    @CreationTimestamp
    @Column(updatable = false)
    private java.util.Date createdAt;

    @UpdateTimestamp
    private java.util.Date updatedAt;

    @NotBlank(groups = UpdateGroup.class)
    @Column(nullable = false)
    protected String company;

    @NotBlank(groups = UpdateGroup.class)
    @Column(nullable = false)
    protected String companyAddress;

    @NotBlank(groups = UpdateGroup.class)
    @Column(nullable = false)
    protected String phone;

    protected String emailVerificationToken;
}