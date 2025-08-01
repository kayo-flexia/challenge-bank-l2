package com.appbanco.demo.repository;

import com.appbanco.demo.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByUsuario(String usuario);
    boolean existsByUsuario(String usuario);
}