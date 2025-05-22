package org.romain.demo2.security;

import lombok.Getter;
import org.romain.demo2.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class AppUserDetails implements UserDetails {

    protected User user; //Contient l'utilisateur

    public AppUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // gestion des rôles et des droits avec Enum
        return List.of(new SimpleGrantedAuthority(
                "ROLE_" + user.getRole().name()));//ROLE_ ici évite de l'utiliser dans la base de donnée

    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.getEmailVerificationToken() == null;
    }
//    #TODO



}
