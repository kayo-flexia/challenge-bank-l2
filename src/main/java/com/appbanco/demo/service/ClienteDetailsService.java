package com.appbanco.demo.service;

import com.appbanco.demo.entity.Cliente;
import com.appbanco.demo.repository.ClienteRepository; // Importamos ClienteRepository directamente
import com.appbanco.demo.security.ClienteDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteDetailsService implements UserDetailsService { // Implementa la interfaz UserDetailsService de spribg

    private final ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByUsuario(username)
                //Si no se encuentra el usuario, lanza una excepción estándar de Spring Security
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // si el cliente se encuentra, lo envuelve en ClienteDetails y lo devuelve a Spring Security
        return new ClienteDetails(cliente);
    }
}
