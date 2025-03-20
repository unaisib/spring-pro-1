package com.bcast.ai_demo.controllers;

import com.bcast.ai_demo.dtos.AuthenticationRequest;
import com.bcast.ai_demo.dtos.AuthResponse;
import com.bcast.ai_demo.dtos.RegistrationRequest;
import com.bcast.ai_demo.models.User;
import com.bcast.ai_demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.bcast.ai_demo.services.CustomUserDetailsService;
import com.bcast.ai_demo.utils.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        if (userService.existsByUsername(registrationRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = userService.registerNewUser(registrationRequest);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String accessToken = jwtUtil.generateAccessToken(userDetails);
        final String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            String token = refreshToken.substring(7);
            String username = jwtUtil.extractUsername(token);
            
            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                if (jwtUtil.validateToken(token, userDetails)) {
                    String newAccessToken = jwtUtil.generateAccessToken(userDetails);
                    String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);
                    
                    return ResponseEntity.ok(new AuthResponse(newAccessToken, newRefreshToken));
                }
            }
        }
        
        return ResponseEntity.badRequest().body("Invalid refresh token");
    }
}