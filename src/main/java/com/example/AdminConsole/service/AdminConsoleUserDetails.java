package com.example.AdminConsole.service;

import com.example.AdminConsole.config.domain.AdminConsoleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AdminConsoleUserDetails implements UserDetails {

    private AdminConsoleUser employeeUser;

    @Autowired
    public AdminConsoleUserDetails(AdminConsoleUser employeeUser) {
        this.employeeUser = employeeUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<SimpleGrantedAuthority> collect =
                Arrays.stream(employeeUser.getAuthorities()).map(e -> new SimpleGrantedAuthority(e)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public String getPassword() {
        return employeeUser.getPassword();
    }

    @Override
    public String getUsername() {
        return employeeUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
