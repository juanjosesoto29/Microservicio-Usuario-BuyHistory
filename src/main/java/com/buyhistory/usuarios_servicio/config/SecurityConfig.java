package com.buyhistory.usuarios_servicio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF (para poder usar Postman/React)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/**").permitAll() // permite libre acceso a tus endpoints REST
                .anyRequest().permitAll()
            )
            .formLogin(form -> form.disable())  // Desactiva el login HTML automático
            .httpBasic(basic -> basic.disable()); // Desactiva auth básica
        return http.build();
    }
}
