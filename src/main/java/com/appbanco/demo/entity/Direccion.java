package com.appbanco.demo.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Embeddable
public class Direccion {
    private String calle;
    private String altura;
    private String ciudad;
    private String provincia;
    private String codigoPostal;
}
