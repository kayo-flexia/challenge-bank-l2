package com.appbanco.demo.controller;

import com.appbanco.demo.dto.ClienteRequestDTO;
import com.appbanco.demo.dto.ClienteResponseDTO;
import com.appbanco.demo.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<ClienteResponseDTO> registrarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteResponse = clienteService.registrarCliente(clienteRequestDTO);
        return new ResponseEntity<>(clienteResponse, HttpStatus.CREATED);
    }
}


