package com.example.AdminConsole.config.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminConsoleUser {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String profession;

    private Date joinDate;

    private String role;

    private String [] authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
