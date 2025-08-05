package com.appbanco.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferenciaId;

    private LocalDateTime fecha;

    private Double monto;

    private String descripcion;

    private String estado; // Puede ser EXITOSA, RECHAZADA, PENDIENTE, etc.

    @ManyToOne
    @JoinColumn(name = "cuenta_origen_id", nullable = false)
    private Cuenta cuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id", nullable = false)
    private Destinatario cuentaDestino; // Es un destinatario agendado
}

