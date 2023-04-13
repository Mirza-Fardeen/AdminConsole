package com.example.AdminConsole.config.domain.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminData {

    private String remoteAddress;

    private String lastUpdate;

    private String type;

    private String message;

    private long execution;
}

