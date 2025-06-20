package org.romain.demo2.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service // pour être injecté
public class SecurityUtils implements ISecurityUtils {

    @Value("${jwt.secret}") //Fais le lien avec application.properties
    String jwtSecret;

    @Override
    public String getRole(AppUserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(r -> r.getAuthority())
                .findFirst()
                .orElse(null);
    }

    @Override
    public String generateToken(AppUserDetails userDetails) {

        //System.out.println(jwtSecret);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .addClaims(Map.of("role", getRole(userDetails)))
                .addClaims(Map.of("userId", userDetails.getUserId()))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
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
