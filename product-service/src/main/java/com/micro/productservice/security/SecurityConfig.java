package com.micro.productservice.security;

import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

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
                                        .requestMatchers("/update/**").hasRole("ADMIN")
                                        .requestMatchers("/get").hasAnyRole("ADMIN","USER")
                                        .requestMatchers("/buy").hasRole("USER")
                                        .requestMatchers("/return/**").hasRole("USER")
                                        .requestMatchers("/create/**").hasRole("USER")
                                        .requestMatchers("/history").hasRole("USER")
                                        .requestMatchers("/keyword/**").hasRole("ADMIN")
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
