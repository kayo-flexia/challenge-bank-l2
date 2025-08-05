package com.appbanco.demo;

import com.appbanco.demo.dto.DestinatarioResponseDTO;
import com.appbanco.demo.entity.Cliente;
import com.appbanco.demo.entity.Destinatario;
import com.appbanco.demo.repository.ClienteRepository;
import com.appbanco.demo.repository.DestinatarioRepository;
import com.appbanco.demo.service.DestinatarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Habilita la integración con Mockito
class DestinatarioServiceImplTest {

    // Repositorios que el servicio necesita, los simulamos con @Mock
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private DestinatarioRepository destinatarioRepository;

    // El servicio que vamos a probar. @InjectMocks inyecta los mocks de arriba
    // automáticamente en este objeto.
    @InjectMocks
    private DestinatarioServiceImpl destinatarioService;

    // Variables de prueba que se inicializan antes de cada test
    private Cliente cliente;
    private Destinatario destinatario1;
    private Destinatario destinatario2;
    private final String username = "testuser";

    @BeforeEach // Se ejecuta antes de cada método de test
    void setUp() {
        // Creamos un cliente de prueba
        cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setUsuario(username);

        // Creamos dos destinatarios de prueba asociados a ese cliente
        destinatario1 = new Destinatario();
        destinatario1.setDestinatarioId(10L);
        destinatario1.setDescripcion("Destinatario 1");
        destinatario1.setAlias("dest1");
        destinatario1.setCbu("123456789");
        destinatario1.setFechaAlta(LocalDate.now());
        destinatario1.setCliente(cliente);

        destinatario2 = new Destinatario();
        destinatario2.setDestinatarioId(11L);
        destinatario2.setDescripcion("Destinatario 2");
        destinatario2.setAlias("dest2");
        destinatario2.setCbu("987654321");
        destinatario2.setFechaAlta(LocalDate.now());
        destinatario2.setCliente(cliente);
    }

    @Test
    void obtenerDestinatarios_debeRetornarListaDeDestinatarios() {
        // 1. Configurar los mocks (WHEN):
        // Cuando el clienteRepository.findByUsuario(username) es llamado,
        // debe retornar un Optional que contiene a nuestro cliente de prueba.
        when(clienteRepository.findByUsuario(username)).thenReturn(Optional.of(cliente));

        // Cuando el destinatarioRepository.findByClienteClienteId(clienteId) es llamado,
        // debe retornar una lista con nuestros dos destinatarios de prueba.
        when(destinatarioRepository.findByClienteClienteId(cliente.getClienteId()))
                .thenReturn(Arrays.asList(destinatario1, destinatario2));

        // 2. Llamar al método del servicio que estamos probando (ACT):
        List<DestinatarioResponseDTO> result = destinatarioService.obtenerDestinatarios(username);

        // 3. Verificar el resultado (ASSERT):
        // Verificamos que la lista retornada no sea nula
        // Verificamos que el tamaño de la lista sea 2
        assertEquals(2, result.size());

        // Verificamos que los datos del primer DTO sean correctos
        assertEquals(destinatario1.getDestinatarioId(), result.get(0).getDestinatarioId());
        assertEquals(destinatario1.getDescripcion(), result.get(0).getDescripcion());
        assertEquals(destinatario1.getCbu(), result.get(0).getCbu());

        // Verificamos que los datos del segundo DTO sean correctos
        assertEquals(destinatario2.getDestinatarioId(), result.get(1).getDestinatarioId());
        assertEquals(destinatario2.getDescripcion(), result.get(1).getDescripcion());
        assertEquals(destinatario2.getCbu(), result.get(1).getCbu());
    }
}

