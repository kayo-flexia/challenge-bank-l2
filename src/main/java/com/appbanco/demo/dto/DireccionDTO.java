package com.appbanco.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DireccionDTO {
    private String calle;
    private String altura;
    private String ciudad;
    private String provincia;
    private String codigoPostal;
}
