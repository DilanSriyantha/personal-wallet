package com.dilansriyantha.backend.Authentication.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dilansriyantha.backend.Authentication.DTOs.AuthResponse;
import com.dilansriyantha.backend.Authentication.DTOs.LoginRequest;
import com.dilansriyantha.backend.Authentication.DTOs.RefreshRequest;
import com.dilansriyantha.backend.Authentication.DTOs.RegisterRequest;
import com.dilansriyantha.backend.Authentication.Services.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @GetMapping("/")
    public @ResponseBody ResponseEntity<String> handleRootRequest() {
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<AuthResponse> handleRegisterRequest(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<AuthResponse> handleLoginRequest(@RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public @ResponseBody ResponseEntity<AuthResponse> handleRefreshRequest(@RequestBody RefreshRequest request) throws Exception {
        return ResponseEntity.ok(authService.refresh(request));
    }   
}
