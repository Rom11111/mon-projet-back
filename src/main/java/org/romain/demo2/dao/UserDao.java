package org.romain.demo2.dao;


import org.romain.demo2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email); //Permet de récupérer un User par son Email
    Optional<User> findByEmailVerificationToken(String token);

}
