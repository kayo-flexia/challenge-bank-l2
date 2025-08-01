package com.appbanco.demo.service;

import com.appbanco.demo.dto.ClienteRequestDTO;
import com.appbanco.demo.dto.ClienteResponseDTO;

public interface ClienteService {
    ClienteResponseDTO registrarCliente(ClienteRequestDTO clienteRequestDTO);
}
