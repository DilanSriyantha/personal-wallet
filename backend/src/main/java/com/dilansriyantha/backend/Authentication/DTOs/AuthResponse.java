package com.dilansriyantha.backend.Authentication.DTOs;

import com.dilansriyantha.backend.Enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse{

    private String name;

    private String email;

    private Role role;
    
    private String accessToken;

    private String refreshToken;
}
