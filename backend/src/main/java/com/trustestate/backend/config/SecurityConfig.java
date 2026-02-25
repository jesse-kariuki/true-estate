package com.trustestate.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtValidator jwtValidator;

    public SecurityConfig(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                            .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                            .requestMatchers("/api/auth/**").permitAll()
                            .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/properties/search/**").permitAll()
                            .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/properties/public/**").permitAll()
                                .requestMatchers("/api/profile/tenant/**").hasRole("TENANT")
                                .requestMatchers("/api/profile/landlord/**").hasRole("LANDLORD")
                            .requestMatchers("/api/properties/manage/**").hasRole("LANDLORD")
                            .requestMatchers("/api/verification/request/**").hasRole("LANDLORD")
                            .requestMatchers("/api/payments/rent/**").hasRole("TENANT")
                            .requestMatchers("/api/reputation/my-score").hasRole("TENANT")
                            .requestMatchers("/api/trust-engine/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                        )

                .addFilterBefore(jwtValidator, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000","https://esit-groceries-pos.vercel.app"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT","PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*")); 
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}