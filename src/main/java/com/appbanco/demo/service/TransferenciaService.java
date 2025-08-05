package com.appbanco.demo.service;

import com.appbanco.demo.dto.TransferenciaRequestDTO;
import com.appbanco.demo.dto.TransferenciaResponseDTO;

public interface TransferenciaService {
    TransferenciaResponseDTO realizarTransferencia(TransferenciaRequestDTO dto, String username);

    TransferenciaResponseDTO obtenerComprobante(Long transferenciaId, String username);
}
