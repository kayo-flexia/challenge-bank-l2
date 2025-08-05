package com.appbanco.demo.repository;

import com.appbanco.demo.entity.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinatarioRepository extends JpaRepository<Destinatario, Long> {
    List<Destinatario> findByClienteClienteId(Long clienteId);
    boolean existsByClienteClienteIdAndCbu(Long clienteId, String cbu); // Validar duplicados
    boolean existsByClienteClienteIdAndAlias(Long clienteId, String alias); // Validar duplicados
}
