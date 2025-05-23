package org.romain.demo2.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) //L'annotation est présente dans le bytecode et est disponible au moment de l'exécution
@PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
public @interface IsClient {


}
