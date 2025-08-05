package com.appbanco.demo.service;

import com.appbanco.demo.dto.CuentaResponseDTO;
import java.util.List;

public interface CuentaService {
    List<CuentaResponseDTO> obtenerCuentasPorUsuario(String username);
}