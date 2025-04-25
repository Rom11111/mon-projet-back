package org.romain.demo2.mock;

import org.romain.demo2.security.AppUserDetails;
import org.romain.demo2.security.ISecurityUtils;

public class MockSecurityUtils implements ISecurityUtils {

    private String role;

    public MockSecurityUtils(String role) {
        this.role = role;
    }

    @Override
    public String getRole(AppUserDetails userDetails) {
        return "USER";
    }

    @Override
    public String generateToken(AppUserDetails userDetails) {
        return "";
    }

    @Override
    public String getSubjectFromJwt(String jwt) {
        return "";
    }
}
