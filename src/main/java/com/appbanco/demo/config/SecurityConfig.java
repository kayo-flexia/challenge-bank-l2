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

    private final JwtAuthFilter jwtAuthFilter; //JwtAuthFilter es el responsable de interceptar y procesar cada solicitud HTTP entrante a tu aplicación para determinar si contiene un token JWT válido. Aún no está implementado
    private final ClienteDetailsService clienteDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // Este método configura las reglas de seguridad.
        http
                // 1. Deshabilitar CSRF (Cross-Site Request Forgery)
                .csrf(csrf -> csrf.disable())

                // 2. Configurar reglas de autorización de peticiones HTTP
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/api/clientes/registrar", "/h2-console/**").permitAll() // Rutas públicas
                        .requestMatchers("/api/cuentas/**").hasRole("USER")
                        .anyRequest().authenticated() // Cualquier otra petición requiere autenticación
                )
                // 3. Configuración de gestión de sesiones a STATELESS para JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Configurar el AuthenticationProvider
                .authenticationProvider(authenticationProvider())

                // 5. Añadir el filtro JWT antes del filtro de autenticación de usuario/contraseña
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                // 6. Configuración de cabeceras HTTP (para H2 Console)
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Permitir frames para H2 Console
                );

        return http.build();
    }

    // 7. Define el AuthenticationProvider para usar tu UserDetailsService y PasswordEncoder
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(clienteDetailsService); // Tu implementación de UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder()); // Tu PasswordEncoder
        return authProvider;
    }

    // 8. Bean para el AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 9. Bean para el PasswordEncoder (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

        //{
        //"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFucGVyZXoiLCJpYXQiOjE3NTQwNTc1NzUsImV4cCI6MTc1NDA2MTE3NX0.XNGjuwbfixEUDDKyweXj13rc9DKYTzWpcZHY-0hNSlo"
        //}