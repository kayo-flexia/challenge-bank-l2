package com.appbanco.demo.controller;

import com.appbanco.demo.dto.CuentaResponseDTO;
import com.appbanco.demo.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaResponseDTO>> obtenerCuentasDelCliente(Principal principal) {
        String username = principal.getName();  // Obtiene el usuario autenticado
        List<CuentaResponseDTO> cuentas = cuentaService.obtenerCuentasPorUsuario(username);
        return ResponseEntity.ok(cuentas);
    }
}
