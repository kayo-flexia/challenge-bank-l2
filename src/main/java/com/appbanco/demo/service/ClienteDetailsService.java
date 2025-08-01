package com.appbanco.demo.service;

import com.appbanco.demo.entity.Cliente;
import com.appbanco.demo.repository.ClienteRepository;
import com.appbanco.demo.security.ClienteDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // 1. Marca esta clase como un componente de servicio de Spring
@RequiredArgsConstructor // 2. Genera un constructor para inyectar ClienteRepository
public class ClienteDetailsService implements UserDetailsService { // 3. Implementa la interfaz UserDetailsService

    private final ClienteRepository clienteRepository; // 4. Inyecta tu repositorio para acceder a la base de datos

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 5. Método principal: Spring Security lo llama para cargar los detalles del usuario
        // 6. Busca el cliente por su nombre de usuario en la base de datos
        Cliente cliente = clienteRepository.findByUsuario(username)
                // 7. Si no se encuentra el usuario, lanza una excepción estándar de Spring Security
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // 8. Si el cliente se encuentra, lo envuelve en tu ClienteDetails y lo devuelve a Spring Security
        return new ClienteDetails(cliente);
    }
}
