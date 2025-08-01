package com.appbanco.demo.security;

import com.appbanco.demo.service.ClienteDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter { // hacemos que doFilterInternal se ejecute una vez por cada solicitud HTTP

    private final JwtUtils jwtUtils;
    private final ClienteDetailsService clienteDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        } // si el encabezado es null o no empieza con 'Bearer', el filtro no procesa el token JWT.

        String token = authHeader.substring(7); //tomamos el token
        String username = jwtUtils.extractUsername(token); //recibimos el nombre de usuario del token

        // Pedimos que haya token y que no haya ya un usuario autenticado en el contexto
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = clienteDetailsService.loadUserByUsername(username); // pedimos la info del usuario

            if (jwtUtils.isTokenValid(token)) { //pedimos que el token sea auténtico
                UsernamePasswordAuthenticationToken authToken = // creamos el objeto de Autenticacion
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities() //roles
                        );
                SecurityContextHolder.getContext().setAuthentication(authToken); //se autentica al usuario
            }
        }

        filterChain.doFilter(request, response); // completamos el filtro y pasamos al siguiente paso
    }
}
