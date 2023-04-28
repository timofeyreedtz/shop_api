package com.micro.notificationservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private  JwtAuthConverter jwtAuthConverter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> {
                            try {
                                authz
                                        .requestMatchers("/send/**").hasRole("ADMIN")
                                        .requestMatchers("/get").hasAnyRole("USER")
                                        .anyRequest().authenticated()
                                        .and()
                                        .oauth2ResourceServer()
                                        .jwt()
                                        .jwtAuthenticationConverter(jwtAuthConverter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
        return http.build();
    }
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


}
