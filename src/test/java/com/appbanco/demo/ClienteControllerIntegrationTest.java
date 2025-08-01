package com.appbanco.demo;

import com.appbanco.demo.dto.ClienteRequestDTO;
import com.appbanco.demo.dto.DireccionDTO;
import com.appbanco.demo.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerIntegrationTest {

    @Autowired // Inyección de dependencia de MockMvc
    private MockMvc mockMvc;

    @Autowired // Inyección de dependencia de ObjectMapper. Convierte los DTOs en cadenas JSON
    private ObjectMapper objectMapper;

    @Autowired // Inyección de dependencia del repositorio de clientes
    private ClienteRepository clienteRepository;

    @BeforeEach // Método que se ejecuta antes de cada test
    void setup() {
        clienteRepository.deleteAll(); // Limpia la base de datos antes de cada test
    }

    @Test // Anotación que marca este método como un test
    void registrarCliente_DeberiaCrearClienteYCuenta() throws Exception {
        // Preparar DTO de prueba
        ClienteRequestDTO clienteRequest = new ClienteRequestDTO();
        clienteRequest.setCuil("20-99999999-9");
        clienteRequest.setDni("99999999");
        clienteRequest.setSexo("M");
        clienteRequest.setApellido("Test");
        clienteRequest.setNombre("Usuario");
        clienteRequest.setEmail("test@example.com");
        clienteRequest.setTelefono("123456789");
        clienteRequest.setUsuario("testuser");
        clienteRequest.setClave("password");

        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setCalle("Falsa");
        direccionDTO.setAltura("123");
        direccionDTO.setCiudad("Springfield");
        direccionDTO.setProvincia("Buenos Aires");
        direccionDTO.setCodigoPostal("1234");

        clienteRequest.setDireccion(direccionDTO);

        // Ejecutar request y validar respuesta
        mockMvc.perform(post("/api/clientes/registrar") // Simula una petición POST a la URL
                        .contentType(MediaType.APPLICATION_JSON) // Establece el Content-Type a JSON
                        .content(objectMapper.writeValueAsString(clienteRequest))) // Convierte el DTO a JSON y lo envía como cuerpo
                .andExpect(status().isCreated()) // 10. Espera un código de estado HTTP 201 (Created)
                .andExpect(jsonPath("$.clienteId").isNumber())
                .andExpect(jsonPath("$.nombre").value("Usuario"))
                .andExpect(jsonPath("$.apellido").value("Test"))
                .andExpect(jsonPath("$.usuario").value("testuser"))
                .andExpect(jsonPath("$.estado").value("ACTIVO"))
                .andExpect(jsonPath("$.cuentas", hasSize(1)))
                .andExpect(jsonPath("$.cuentas[0].numeroCuenta").isString())
                .andExpect(jsonPath("$.cuentas[0].cbu").isString())
                .andExpect(jsonPath("$.cuentas[0].tipoCuenta").value("CAJA_DE_AHORRO"))
                .andExpect(jsonPath("$.cuentas[0].saldoActual").value(0.0));
    }
}
