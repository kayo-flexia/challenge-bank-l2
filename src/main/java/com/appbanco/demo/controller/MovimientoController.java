package com.appbanco.demo.controller;

import com.appbanco.demo.dto.MovimientoResponseDTO;
import com.appbanco.demo.service.MovimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping("/{cuentaId}")
    public ResponseEntity<List<MovimientoResponseDTO>> obtenerMovimientosPorCuenta(
            @PathVariable Long cuentaId,
            Principal principal
    ) {
        String username = principal.getName(); // Usuario autenticado desde el token JWT

        List<MovimientoResponseDTO> movimientos = movimientoService.obtenerMovimientosPorCuenta(cuentaId, username);

        return ResponseEntity.ok(movimientos);
    }
}
