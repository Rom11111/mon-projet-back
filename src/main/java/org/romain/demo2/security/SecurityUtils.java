package org.romain.demo2.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service // pour être injecté
public class SecurityUtils implements ISecurityUtils {

    @Value("${jwt.secret}") //Fais le lien avec application.properties
    String jwtSecret;

    @Override
    public String getRole(AppUserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String generateToken(AppUserDetails userDetails) {

        return Jwts.builder()
                // Sujet du token → ici l’email de l’utilisateur (identifiant unique)
                .setSubject(userDetails.getUsername())
                // Ajout d’informations supplémentaires (claims) :
                // - le rôle de l’utilisateur
                .addClaims(Map.of("role", getRole(userDetails)))
                // - l’identifiant de l’utilisateur
                .addClaims(Map.of("userId", userDetails.getUserId()))
                // Signature du token avec l’algorithme HS256 et une clé secrète
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                // Génération finale du token sous forme de chaîne compacte
                .compact();
    }

    @Override
    public String getSubjectFromJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(jwtSecret) // jwtsecret à trouver en variable d'environnement.
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}
