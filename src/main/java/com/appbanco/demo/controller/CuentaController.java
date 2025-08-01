package com.appbanco.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @GetMapping
    public ResponseEntity<String> getCuentas() {
        return ResponseEntity.ok("Acceso permitido a cuentas.");
    }
}
