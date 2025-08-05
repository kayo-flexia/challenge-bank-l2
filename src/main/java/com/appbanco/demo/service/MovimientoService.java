package com.appbanco.demo.service;

import com.appbanco.demo.dto.MovimientoResponseDTO;

import java.util.List;

public interface MovimientoService {
    List<MovimientoResponseDTO> obtenerMovimientosPorCuenta(Long cuentaId, String username);
}
