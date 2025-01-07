package org.example.backendtest.controller;

import org.example.backendtest.entities.User;
import org.example.backendtest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint pour créer un nouvel utilisateur
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // Vérifier si l'utilisateur existe déjà
        if (userService.usernameExists(user.getUsername())) {
            return ResponseEntity.badRequest().body("Nom d'utilisateur déjà pris");
        }

        // Créer l'utilisateur
        userService.createUser(user);

        return ResponseEntity.status(201).body(user);  // Retourner l'utilisateur créé
    }

    // Endpoint pour connecter un utilisateur (login)
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        // Vérifier si l'utilisateur existe
        User user = userService.findUserByUsername(loginRequest.getUsername());

        if (user == null || !userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Nom d'utilisateur ou mot de passe incorrect");
        }

        // Générer un token JWT
        String token = userService.generateToken(user);

        // Retourner le token JWT
        return ResponseEntity.ok().body("Bearer " + token);
    }
}
