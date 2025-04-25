package org.romain.demo2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping({"/", "/hello"}) // Indique la route pour l'url
    public String sayhello() {
        return "<h1>Le serveur marche mais il n'y a rien à voir ici</h1>";
    }

}
