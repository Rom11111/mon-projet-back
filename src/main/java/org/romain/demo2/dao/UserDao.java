package org.romain.demo2.dao;

import org.romain.demo2.model.Role;
import org.romain.demo2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    //Permet de récupérer un User par son Email
    Optional<User> findByEmail(String email);

    // Récupère tous les utilisateurs d’un rôle donné
    Optional<User> findByEmailVerificationToken(String token);
    List<User> findByRole(Role role);
}
