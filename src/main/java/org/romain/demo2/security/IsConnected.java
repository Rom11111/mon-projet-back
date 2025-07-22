package org.romain.demo2.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TECH', 'ROLE_CLIENT')")
public @interface IsConnected {
}
