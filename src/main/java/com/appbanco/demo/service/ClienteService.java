package com.appbanco.demo.service;

import com.appbanco.demo.dto.ClienteRequestDTO;
import com.appbanco.demo.dto.ClienteResponseDTO;
import com.appbanco.demo.entity.Cliente;

public interface ClienteService {
    ClienteResponseDTO registrarCliente(ClienteRequestDTO clienteRequestDTO);
    // método para buscar un cliente que será cacheado
    // elijo cachear el cliente porque se cnosulta la entidad Cliente logueada en muchos casos. Un cliente puede cambiar su información desde algún menú de configuración, pero es muy poco probable.
    // Entonces: como la entidad cliente se consulta seguido y prácticamente no se cambia, ahorramos muchisimas consulas a la bbdd
    // Incluso si realiza multiples transferencias, la entidad Cuenta es quien lleva el saldo. La entidad Cliente almacena información personal
    Cliente findByUsuario(String username);
}
