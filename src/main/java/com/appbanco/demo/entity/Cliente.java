package com.appbanco.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity // Esta anotación es de JPA y le dice a Hibernate (la implementación de JPA que usa Spring Data JPA) que esta clase es una entidad
@Getter @Setter //de Lombock
public class Cliente {
    @Id //para marcar la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a la base de datos que la columna clienteId será una columna de identidad auto-incrementada
    private Long clienteId;

    private String cuil;
    private String dni;
    private String sexo;
    private String apellido;
    private String nombre;
    private String estado;
    private String email;
    private String telefono;
    private String usuario;
    private String clave;
    private String rol;

    @Embedded // Esta anotación se utiliza para incrustar los atributos de otra clase en la tabla de la entidad actual
    private Direccion direccion;


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Cuenta> cuentas;
}
