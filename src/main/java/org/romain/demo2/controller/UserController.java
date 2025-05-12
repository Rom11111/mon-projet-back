package org.romain.demo2.controller;

import org.romain.demo2.dao.UserDao;
import org.romain.demo2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200") // Permet au front Angular d'accéder à l'API
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping
    public List<User> getAllUsers() {
        return userDao.findAll();
    }


}
