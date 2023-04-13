package com.example.AdminConsole.enumeration;

import static com.example.AdminConsole.constant.Authority.ADMIN_AUTHORITIES;
import static com.example.AdminConsole.constant.Authority.USER_AUTHORITIES;

public enum Role {

    ROLE_USER(USER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES);

    private String[] authorities;
    Role(String[] userAuthorities) {
        this.authorities= userAuthorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
