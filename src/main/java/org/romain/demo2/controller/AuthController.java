package org.romain.demo2.controller;

import jakarta.validation.Valid;
import org.romain.demo2.dao.UserDao;
import org.romain.demo2.dto.EmailValidationDto;
import org.romain.demo2.model.Role;
import org.romain.demo2.model.User;
import org.romain.demo2.security.AppUserDetails;
import org.romain.demo2.security.ISecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
public class AuthController {

    protected AuthenticationProvider authenticationProvider;
    protected UserDao userDao;
    protected PasswordEncoder passwordEncoder;
    protected ISecurityUtils securityUtils;

    @Autowired
    public AuthController(AuthenticationProvider authenticationProvider, UserDao userDao, PasswordEncoder passwordEncoder, ISecurityUtils securityUtils) {
        this.authenticationProvider = authenticationProvider;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.securityUtils = securityUtils;
    }


    @PostMapping("/signin") // Gère l'inscription
    public ResponseEntity<User> signin(@RequestBody @Validated(User.RegistrationGroup.class) User user) {

        //user.setRole(Role.CLIENT); //Simple User lors de la création
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String emailVerificationToken = UUID.randomUUID().toString();

        user.setEmailVerificationToken(emailVerificationToken);

        user.setCompany("A renseigner");
        user.setCompanyAddress("A renseigner");
        user.setFirstname("A renseigner");
        user.setLastname("A renseigner");
        user.setPhone("A renseigner");


        userDao.save(user);



        //On masque le mot de passe
        user.setPassword(null);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login") // Gère la connection
    public ResponseEntity<String> login(@RequestBody User user) {

        try {
            AppUserDetails userDetails = (AppUserDetails) authenticationProvider.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(),
                                    user.getPassword()))
                    .getPrincipal();

            return new ResponseEntity<>(securityUtils.generateToken(userDetails), HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // fait une erreur 401 si le User n'est pas connecté
        }


    }

    @PostMapping("/validate-email")
    public ResponseEntity<User> validateEmail(@RequestBody EmailValidationDto emailValidationDto) {

        Optional<User> user = userDao.findByEmailVerificationToken(emailValidationDto.getToken());

        if (user.get().getEmailVerificationToken().equals(emailValidationDto.getToken())) {
            user.get().setEmailVerificationToken(null);
            userDao.save(user.get());
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}

