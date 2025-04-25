//Permet de faire des dépendence faible entre les classes
package org.romain.demo2.security;

public interface ISecurityUtils {
    String getRole(AppUserDetails userDetails);

    String generateToken(AppUserDetails userDetails);

    String getSubjectFromJwt(String jwt);
}
