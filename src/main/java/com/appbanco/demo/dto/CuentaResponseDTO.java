package com.appbanco.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CuentaResponseDTO {
    private Long cuentaId;
    private String numeroCuenta;
    private String cbu;
    private String alias;
    private String tipoCuenta;
    private String moneda;
    private Double saldoActual;
    private String estado;
}
