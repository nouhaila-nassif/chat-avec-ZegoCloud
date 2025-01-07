package org.example.backendtest.repositories;


import org.example.backendtest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Rechercher un utilisateur par son nom d'utilisateur
    Optional<User> findByUsername(String username);

    // Rechercher un utilisateur par son email (si applicable)
    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);
}

