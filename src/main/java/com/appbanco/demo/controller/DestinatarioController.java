package com.appbanco.demo.controller;

import com.appbanco.demo.dto.DestinatarioRequestDTO;
import com.appbanco.demo.dto.DestinatarioResponseDTO;
import com.appbanco.demo.service.DestinatarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinatarios")
@RequiredArgsConstructor
public class DestinatarioController {

    private final DestinatarioService destinatarioService;

    @PostMapping
    public void agendarDestinatario(@RequestBody DestinatarioRequestDTO dto,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        destinatarioService.agendarDestinatario(dto, username);
    }

    @GetMapping
    public List<DestinatarioResponseDTO> obtenerDestinatarios(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return destinatarioService.obtenerDestinatarios(username);
    }
}

