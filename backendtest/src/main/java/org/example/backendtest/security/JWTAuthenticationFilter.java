package org.example.backendtest.security;



import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backendtest.entities.User;
import org.example.backendtest.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
// GENERER 3METHODES
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super.setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    //CETTE FONCTION EST GENERER AUTOMATI
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            System.out.println("Tentative d'authentification");
            //OBEJET MAPPER SERE A SERIALISER ET DESERIALISER VERS JSON COMME REPOSI + TYPE DE LEBJET CLASS USER DANS CE CAS
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            System.out.println("Utilisateur tenté : " + user.getUsername());

            return authenticationManager.authenticate(
                    //ETABLIER LE TOKEN ET SPECIALISER LES DONNEES USERNAME ET PASSWORD
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
        } catch (Exception e) {
            // Log de l'exception
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'authentification", e);
        }
    }

    // Si l'authentification est réussie, génère un JWT et l'ajoute dans l'en-tête de la réponse
    @Override
            //METHODE DEJA TOUVER
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        try {
            // Récupération de l'utilisateur authentifié
            // USER DE TYPE USERDETAILS
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            //FAIRE APPEL AAU RESULTAS
                            authResult.getPrincipal();

            System.out.println("Authentification réussie pour : " + user.getUsername());

//            // Récupérer les rôles de l'utilisateur
//            List<String> roles = user.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .toList();

//            // Log des rôles de l'utilisateur
//            System.out.println("Rôles de l'utilisateur : " + roles);

            // Générer le JWT avec les informations de l'utilisateur et de ses rôles
            //DECLARATION DUNE VARIABLE
            //JWT DAPRES AUTH0
            String jwt = JWT.create()
                    //SPECIFEIR CE QUIL VA ETRE DEDANS
                    // Sujet : nom d'utilisateur
                    .withSubject(user.getUsername()) // Sujet : nom d'utilisateur
                    //.withArrayClaim("roles", roles.toArray(new String[0])) // Ajout des rôles de l'utilisateur
                    //DATE DEXPIRATION APATIR DE MA DATE PRESENTE  + DATE SPECIIFER DANS CLASS PARAMETRE
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecurityParameters.EXPIRATION_TIME)) // Date d'expiration du token
                    // Signature du token avec la clé secrète
                    // SPECIFIER LALGO SOIT HMAC SOIT RSA CLEE PRIEVE ET CLEE PUBLIC
                    .sign(Algorithm.HMAC256(SecurityParameters.SECRET)); // Signature du token avec la clé secrète

            // Ajouter l'en-tête Authorization avec le token JWT généré
            response.addHeader("Authorization",  jwt);

            // Log du token généré
            System.out.println("Token généré avec succès: " + jwt);
        } catch (Exception e) {
            // Log de l'exception
            e.printStackTrace();
            throw new ServletException("Erreur lors de la génération du token", e);
        }
    }
}