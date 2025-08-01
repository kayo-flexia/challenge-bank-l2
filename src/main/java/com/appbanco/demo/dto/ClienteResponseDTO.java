package com.appbanco.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ClienteResponseDTO {
    private Long clienteId;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String usuario;
    private String estado;
    private DireccionDTO direccion;

    private List<CuentaResponseDTO> cuentas;
}
