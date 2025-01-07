package org.example.backendtest.services;

import org.example.backendtest.entities.User;
import org.example.backendtest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String secret = "TESTBACKEND";  // Remplacez par une clé secrète plus sûre

    // Créer un utilisateur
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Cryptez le mot de passe
        return userRepository.save(user);  // Sauvegardez l'utilisateur dans la base de données
    }

    // Vérifier si le nom d'utilisateur existe déjà
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // Trouver un utilisateur par son nom d'utilisateur
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec ce nom d'utilisateur"));
    }

    // Trouver un utilisateur par son nom d'utilisateur (si vous souhaitez utiliser cette méthode ailleurs)
    public User findUserByUsername(String username) {
        return getUserByUsername(username);  // Réutilise la méthode getUserByUsername
    }

    // Vérifier si le mot de passe est correct
    public boolean checkPassword(String rawPassword, String storedPassword) {
        return passwordEncoder.matches(rawPassword, storedPassword);  // Vérifie si les mots de passe correspondent
    }

    // Générer un token JWT pour l'utilisateur
    public String generateToken(User user) {
        // Créer un token JWT
        return JWT.create()
                .withSubject(user.getUsername())  // Ajouter le nom d'utilisateur comme sujet
                .withIssuedAt(new Date())  // Date d'émission du token
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))  // Expiration du token dans 24 heures
                .sign(Algorithm.HMAC256(secret));  // Signez le token avec la clé secrète
    }
}
