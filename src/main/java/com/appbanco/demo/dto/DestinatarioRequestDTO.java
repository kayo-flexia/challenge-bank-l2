package com.appbanco.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DestinatarioRequestDTO {
    private String descripcion; //del destinatario
    private String cbu;
    private String alias;
    private String moneda;
    private String cuentaNro;
    //Uso alias y cbu para las validaciones
}