package com.example.AdminConsole.config.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AdminConsoleRequest {

    private String username;

    private String email;

    private String password;

    private String profession;
}
