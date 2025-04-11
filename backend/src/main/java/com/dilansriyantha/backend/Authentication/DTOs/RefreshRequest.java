package com.dilansriyantha.backend.Authentication.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshRequest {
    
    private String name;

    private String email;

    private String refreshToken;
}
