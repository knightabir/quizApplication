package com.quiz.authService;

import com.quiz.dto.LoginResponse;
import com.quiz.dto.LoginUserDto;
import com.quiz.dto.RegisterDto;
import com.quiz.model.User;
import com.quiz.repository.UserRepository;
import com.quiz.security.JwtUtil;
import com.quiz.service.impl.CustomUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public User singUp(RegisterDto registerDto){
        Optional<User> existingUser = userRepository.findByEmail(registerDto.getEmail());
        if (existingUser.isPresent()){
            throw new IllegalArgumentException("User already exists with this email.");
        }
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setFullName(registerDto.getFirstName()+" " + registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
        user.setCreatedAt(System.currentTimeMillis());
        return userRepository.save(user);
    }



    public LoginResponse authenticate(LoginUserDto loginUserDto) {
        Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
        LoginResponse response = new LoginResponse();
        try {
            // Perform authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword())
            );

            // Check if the authentication is successful
            if (authentication.isAuthenticated()) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginUserDto.getEmail());
                // Return the token
                response.setToken(jwtUtil.generateToken(userDetails.getUsername()));
                response.setRole(userDetails.getAuthorities());
            } else {
                logger.error("Authentication failed for user: {}", loginUserDto.getEmail());
                response.setMessage("Authentication failed: Invalid credentials");
            }
        } catch (BadCredentialsException e) {
            logger.error("Invalid credentials provided for user: {}", loginUserDto.getEmail(), e);
            response.setMessage("Authentication failed: Invalid credentials");
        } catch (UsernameNotFoundException e) {
            // Handle user not found
            logger.error("User not found: {}", loginUserDto.getEmail(), e);
            response.setMessage("Authentication failed: Invalid credentials");
        } catch (Exception e) {
            // Handle general exceptions
            logger.error("Authentication error for user: {}", loginUserDto.getEmail(), e);
            response.setMessage("Authentication failed: Invalid credentials");
        }
        return response;
    }
}
