package com.puenteblanco.pb.config;

import com.puenteblanco.pb.security.JwtAuthenticationFilter;
import com.puenteblanco.pb.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/index.html",
                    "/css/**", "/js/**", "/images/**",
                    "/api/auth/**",
                    "/api/reniec/**"
                     // Reniec API pública
                ).permitAll()

                .requestMatchers(
                    "/dashboard",
                    "/calendar",
                    "/book-appointment",
                    "/cancel-appointment",
                    "/appointments/**",
                    "/logout",
                    "/pets/**",
                    "/api/payments/**",
                    "/payment-form",
                    "/api/client/**"
                ).hasRole("CLIENT")

                .requestMatchers(
                    "/vet/**",
                    "/api/vet/**",
                    "/api/vet/reports/**"
                ).hasRole("VETERINARIAN")

                .requestMatchers(
                "/intern/**",
                "/api/intern/**"
                ).hasRole("INTERN")

                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}

