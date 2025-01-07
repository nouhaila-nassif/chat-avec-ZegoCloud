package org.example.backendtest.security;


import org.example.backendtest.entities.User;
import org.example.backendtest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    //DOU JE VAIS IMPOTER LES DONNEES INFO
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //USER DENTITE
        User user = userService.findUserByUsername(username);
        //TOUVER OU NON
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur introuvable");
        }
        //LIRE LES DONNES ROLES DANS UNE LISTE
        //List<GrantedAuthority> authorities = new ArrayList<>();

        // Ajoutez des rôles ou des autorisations ici
        //authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}

