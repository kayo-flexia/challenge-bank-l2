package com.appbanco.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Destinatario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long destinatarioId;

    private String descripcion; // Alias descriptivo que le da el cliente
    private String cbu;
    private String alias;
    private String moneda;
    private String cuentaNro;
    private LocalDate fechaAlta;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; // Relación con Cliente (Muchos Destinatarios pueden pertenecer a un Cliente)
}
