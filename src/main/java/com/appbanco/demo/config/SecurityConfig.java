package com.appbanco.demo.config;

import com.appbanco.demo.security.JwtAuthFilter;
import com.appbanco.demo.service.ClienteDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // genera automáticamente un constructor para esta clase
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final ClienteDetailsService clienteDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Este método configura las reglas de seguridad.
        http
                // Deshabilitar CSRF (Cross-Site Request Forgery)
                .csrf(csrf -> csrf.disable())

                // Configurar reglas de autorización de peticiones HTTP
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/api/clientes/registrar", "/h2-console/**").permitAll() // Rutas públicas
                        .requestMatchers("/api/cuentas/**").hasRole("USER")
                        .anyRequest().authenticated() // Cualquier otra petición requiere autenticación
                )
                // Configuración de gestión de sesiones a STATELESS para JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // onfigurar el AuthenticationProvider
                .authenticationProvider(authenticationProvider())

                // Añadir el filtro JWT antes del filtro de autenticación de usuario/contraseña
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                //  Configuración de cabeceras HTTP (para H2 Console)
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Permitir frames para H2 Console
                );

        return http.build();
    }

    // Define el AuthenticationProvider para usar tu UserDetailsService y PasswordEncoder
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(clienteDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); // Tu PasswordEncoder
        return authProvider;
    }

    // Bean para el AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Bean para el PasswordEncoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
