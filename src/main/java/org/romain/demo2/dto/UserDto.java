package org.romain.demo2.dto;

import org.romain.demo2.model.Role;
import org.romain.demo2.model.UserStatus;

import java.util.Date;

/**
 * DTO pour exposer uniquement les infos utiles et sûres d’un utilisateur.
 */
public record UserDto(
        Long id,
        String firstname,
        String lastname,
        String email,
        Role role,
        String phone,
        String company,
        String companyAddress,
        UserStatus userStatus,
        String photoUrl,
        Date createdAt,
        Date updatedAt
) {
    // Méthode utilitaire pour convertir un User en UserDto
    public static UserDto from(org.romain.demo2.model.User user) {
        if (user == null) return null;

        return new UserDto(
                user.getId(),
                safe(user.getFirstname()),
                safe(user.getLastname()),
                safe(user.getEmail()),
                user.getRole(), // Role est un enum, safe si null
                safe(user.getPhone()),
                safe(user.getCompany()),
                safe(user.getCompanyAddress()),
                user.getUserStatus(), // idem, enum → OK même null
                safe(user.getPhotoUrl()),
                user.getCreatedAt(), // dates peuvent être null → OK
                user.getUpdatedAt()
        );
    }

    // Méthode utilitaire pour éviter les nulls sur les String
    private static String safe(String value) {
        return value != null ? value : "";
    }
}
