package com.quiz.controller;

import com.quiz.authService.AuthenticationService;
import com.quiz.dto.LoginUserDto;
import com.quiz.dto.RegisterDto;
import com.quiz.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterDto registerDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            User registeredUser = authenticationService.singUp(registerDto);
            response.put("status", true);
            response.put("message", "Registration successful.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Registration failed", e);
            response.put("status", false);
            response.put("message", "Registration failed: " + e.getMessage());
            response.put("code", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto){
        try {
            return ResponseEntity.ok(authenticationService.authenticate(loginUserDto));
        } catch (AuthenticationException e) {
            log.error("Authentication failed", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("An error occurred during authentication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
