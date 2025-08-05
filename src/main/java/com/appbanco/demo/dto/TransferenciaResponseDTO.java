package com.appbanco.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransferenciaResponseDTO {

    private Long transferenciaId;
    private LocalDateTime fecha;
    private String cuentaOrigenAlias;  // Alias o número de cuenta origen
    private String cuentaDestinoCbu;   // CBU del destinatario
    private String destinatarioAlias;  // Alias del destinatario (opcional)
    private String estado;
    private Double monto;
    private String descripcion;        // Concepto o descripción opcional
    private Double saldoResultante;    // Saldo de la cuenta origen luego de la transferencia
}
