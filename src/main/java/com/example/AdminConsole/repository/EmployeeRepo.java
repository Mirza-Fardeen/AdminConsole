package com.example.AdminConsole.repository;

import com.example.AdminConsole.config.domain.AdminConsoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<AdminConsoleUser,Long> {
    AdminConsoleUser findByUsername(String username);
}
