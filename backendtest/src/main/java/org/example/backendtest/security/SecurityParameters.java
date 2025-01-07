package org.example.backendtest.security;


public class SecurityParameters {
    // 30 jours
    public static final long EXPIRATION_TIME = 30 * 24 * 60 * 60 * 1000; // 30 jours
    public static final String SECRET = "TESTBACKEND";
    public static final String PREFIX = "Bearer "; // Préfixe pour le token
}
