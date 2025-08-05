package com.appbanco.demo.dto;

import com.appbanco.demo.enums.ConceptoMovimiento;
import com.appbanco.demo.enums.TipoMovimiento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoResponseDTO {

    private Long movimientoId;
    private LocalDateTime fecha;
    private TipoMovimiento tipoMovimiento;
    private ConceptoMovimiento concepto;
    private Double monto;
    private String referencia;
    private Long cuentaId;
    private Double saldoResultante;

}
