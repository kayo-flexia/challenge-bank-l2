package com.appbanco.demo.entity;

import com.appbanco.demo.enums.ConceptoMovimiento;
import com.appbanco.demo.enums.TipoMovimiento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Getter
@Setter
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimientoId;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipoMovimiento; // DEBITO, CREDITO, TRANSFERENCIA, etc.

    @Enumerated(EnumType.STRING)
    private ConceptoMovimiento concepto; // sueldo, pago tarjeta, etc.

    private Double monto;

    private String referencia; // Código de referencia, comprobante, etc.

    private Double saldoResultante; // Saldo de la cuenta luego de aplicar el movimiento.

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;
}