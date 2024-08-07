package com.quiz.security;

import com.quiz.service.impl.CustomUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        try {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authorizeRequests ->
                            authorizeRequests
                                    .requestMatchers("/api/auth/**").permitAll()
                                    .requestMatchers("/api/admin/**").hasAnyAuthority("ROLE_ADMIN")
                                    .requestMatchers("/api/question/**").hasAnyAuthority("ROLE_QUESTION","ROLE_ADMIN")
                                    .requestMatchers("/api/user/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN","ROLE_QUESTION")
                                    .anyRequest().authenticated()
                    )
                    .logout(LogoutConfigurer::permitAll)
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            logger.info("Security filter chain configured successfully.");
            return http.build();
        } catch (Exception e) {
            logger.error("Error configuring security filter chain: ", e);
            throw e;
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        try {
            return authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            logger.error("Error creating authentication manager: ", e);
            throw e;
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
