package com.appbanco.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferenciaRequestDTO {

    private Long cuentaOrigenId;      // ID de la cuenta desde la que se transfiere
    private Long destinatarioId;      // ID del destinatario (agenda_destinatario_id FK)
    private Double monto;             // Monto a transferir
    private String descripcion;       // Concepto o descripción opcional
}

