package com.appbanco.demo.security;

import com.appbanco.demo.entity.Cliente;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class ClienteDetails implements UserDetails { // Implementa la interfaz UserDetails de Spring Security

    private final Cliente cliente; // Almacena una referencia a tu entidad Cliente real

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Si el cliente tiene un rol, lo convertimos a una autoridad de Spring Security
        if (cliente.getRol() != null) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + cliente.getRol().toUpperCase()));
        }
        // Si no tiene rol (por alguna razón), o para clientes ya existentes sin el campo rol
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        // 5. Devuelve la contraseña del cliente.
        // ¡Importante! Esta contraseña DEBE estar encriptada en tu base de datos.
        // Spring Security usará el PasswordEncoder que configuraste (BCrypt) para comparar esta clave encriptada con la clave que el usuario ingrese.
        return cliente.getClave();
    }

    @Override
    public String getUsername() {
        // 6. Devuelve el nombre de usuario del cliente (el campo por el que el usuario intenta loguearse).
        return cliente.getUsuario();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 7. Indica si la cuenta del usuario no ha expirado.
        // Siempre devuelve 'true' por ahora. Si tuvieras una lógica de expiración de cuentas, iría aquí.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 8. Indica si la cuenta del usuario no está bloqueada.
        // Siempre devuelve 'true' por ahora. Si tuvieras una lógica para bloquear cuentas, iría aquí.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 9. Indica si las credenciales (contraseña) del usuario no han expirado.
        // Siempre devuelve 'true' por ahora. Si tuvieras una lógica para forzar cambios de contraseña, iría aquí.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 10. Indica si la cuenta del usuario está habilitada.
        // Aquí utilizas el campo 'estado' de tu Cliente. Solo se permitirá el login si el estado es "ACTIVO".
        return cliente.getEstado().equalsIgnoreCase("ACTIVO");
    }

    public Cliente getCliente() {
        // 11. Método auxiliar para acceder directamente al objeto Cliente subyacente.
        // Esto puede ser útil si necesitas acceder a otras propiedades del Cliente que no están en la interfaz UserDetails.
        return cliente;
    }
}
