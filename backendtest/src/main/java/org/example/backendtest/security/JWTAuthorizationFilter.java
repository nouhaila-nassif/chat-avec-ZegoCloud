package org.example.backendtest.security;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.example.backendtest.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
//SE CONNECTER UNE SEUL EXECUTION UNE SEUL CONX

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //DECLARER VARIABLE
        String jwt = request.getHeader("Authorization");
        //FAIRE DES VERIFICATIONS
        if (jwt==null || !jwt.startsWith(SecurityParameters.PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }
        jwt = jwt.substring(SecurityParameters.PREFIX.length());
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParameters.SECRET)).build();
        DecodedJWT decodedJWT = verifier.verify(jwt);
        String username = decodedJWT.getSubject();
        //List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        //for (String role : roles)
            //authorities.add(new SimpleGrantedAuthority(role));
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
                username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(user);
        filterChain.doFilter(request, response);
    }
}
