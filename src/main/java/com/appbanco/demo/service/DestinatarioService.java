package com.appbanco.demo.service;

import com.appbanco.demo.dto.DestinatarioRequestDTO;
import com.appbanco.demo.dto.DestinatarioResponseDTO;

import java.util.List;

public interface DestinatarioService {
    void agendarDestinatario(DestinatarioRequestDTO dto, String username);
    List<DestinatarioResponseDTO> obtenerDestinatarios(String username);
}
