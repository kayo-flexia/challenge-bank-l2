package com.appbanco.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DestinatarioResponseDTO {
    private Long destinatarioId;
    private String descripcion;
    private String cbu;
    private String alias;
    private String moneda;
    private String cuentaNro;
    private LocalDate fechaAlta;
}
