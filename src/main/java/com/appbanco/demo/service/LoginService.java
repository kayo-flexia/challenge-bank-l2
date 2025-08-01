package com.appbanco.demo.service;

import com.appbanco.demo.dto.LoginRequestDTO;
import com.appbanco.demo.dto.LoginResponseDTO;
import com.appbanco.demo.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        try {
            // 1. Autenticar credenciales
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsuario(),
                            loginRequest.getClave()
                    )
            );

            // 2. Si es exitosa, generar JWT
            String token = jwtUtils.generateToken(loginRequest.getUsuario());

            // 3. Devolver respuesta con el token
            return new LoginResponseDTO(token);

        } catch (AuthenticationException ex) {
            throw new RuntimeException("Usuario o clave inválidos");
        }
    }
}