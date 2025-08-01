package com.appbanco.demo.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter @Setter
public class ClienteRequestDTO {

    @NotBlank(message = "El CUIL es obligatorio")
    private String cuil;

    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    @NotBlank(message = "El sexo es obligatorio")
    private String sexo;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "La dirección es obligatoria")
    private DireccionDTO direccion;

    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "El usuario es obligatorio")
    private String usuario;

    @NotBlank(message = "La clave es obligatoria")
    private String clave;
}
