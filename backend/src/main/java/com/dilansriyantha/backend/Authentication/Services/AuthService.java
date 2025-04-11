package com.dilansriyantha.backend.Authentication.Services;

import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dilansriyantha.backend.Authentication.DTOs.AuthResponse;
import com.dilansriyantha.backend.Authentication.DTOs.LoginRequest;
import com.dilansriyantha.backend.Authentication.DTOs.RefreshRequest;
import com.dilansriyantha.backend.Authentication.DTOs.RegisterRequest;
import com.dilansriyantha.backend.Authentication.Models.User;
import com.dilansriyantha.backend.Authentication.Repositories.UserDAO;
import com.dilansriyantha.backend.Configurations.JwtService;
import com.dilansriyantha.backend.Enums.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserDAO userDAO;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(request.getPassword())
            .role(Role.GUEST)
            .build();
            
        userDAO.create(user);

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder()
            .name(request.getName())
            .email(request.getEmail())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public AuthResponse login(LoginRequest request) throws Exception {
        User user = userDAO.getByEmail(request.getEmail())
            .orElseThrow(() -> new Exception("User not found."));

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder()
            .name(user.getName())
            .email(user.getEmail())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public AuthResponse refresh(RefreshRequest request) throws Exception { 
        var token = request.getRefreshToken();

        var email = jwtService.extractEmail(token);

        if(email == null)
            throw new BadRequestException("Invalid refresh token.");

        var user = userDAO.getByEmail(email)
            .orElseThrow(() -> new Exception("User not found"));

        if(!jwtService.isTokenValid(token, user))
            throw new Exception("Refresh token is invalid.");

        var accessToken = jwtService.generateAccessToken(user);

        return AuthResponse.builder()
            .name(user.getName())
            .email(user.getEmail())
            .role(user.getRole())
            .accessToken(accessToken)
            .refreshToken(request.getRefreshToken())
            .build();
    }
}
