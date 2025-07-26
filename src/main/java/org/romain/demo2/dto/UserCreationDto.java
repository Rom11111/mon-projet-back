package org.romain.demo2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.romain.demo2.model.Role;
import org.romain.demo2.model.UserStatus;

@Getter
@Setter
public class UserCreationDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    private String company;

    @NotBlank
    private String companyAddress;

    @NotBlank
    private String phone;

    @NotNull
    private Role role;

    private UserStatus userStatus = UserStatus.ACTIVE; // Valeur par défaut
}
