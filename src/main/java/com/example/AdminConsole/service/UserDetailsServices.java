package com.example.AdminConsole.service;

import com.example.AdminConsole.config.domain.AdminConsoleUser;
import com.example.AdminConsole.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServices implements UserDetailsService {

    private EmployeeRepo employeeRepo;

    @Autowired
    public UserDetailsServices(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AdminConsoleUser byUsername = employeeRepo.findByUsername(username);
        if(byUsername!=null){
            final AdminConsoleUserDetails employeeUserDetails = new AdminConsoleUserDetails(byUsername);
            return employeeUserDetails;
        }
        return null;
    }
}
