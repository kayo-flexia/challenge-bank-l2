package com.appbanco.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaId;

    private String numeroCuenta;
    private String cbu;
    private String alias;
    private String tipoCuenta;
    private String moneda;
    private Double saldoActual;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movimiento> movimientos;
}
