package com.appbanco.demo.service;

import com.appbanco.demo.dto.DestinatarioRequestDTO;
import com.appbanco.demo.dto.DestinatarioResponseDTO;
import com.appbanco.demo.entity.Cliente;
import com.appbanco.demo.entity.Destinatario;
import com.appbanco.demo.repository.DestinatarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DestinatarioServiceImpl implements DestinatarioService {

    private final ClienteService clienteService; // Inyectamos el servicio para usar la caché
    private final DestinatarioRepository destinatarioRepository;

    @Override
    @Transactional
    public void agendarDestinatario(DestinatarioRequestDTO dto, String username) {
        // Buscar cliente por username usando el servicio cacheado
        Cliente cliente = clienteService.findByUsuario(username);

        // Validar duplicados
        if (destinatarioRepository.existsByClienteClienteIdAndCbu(cliente.getClienteId(), dto.getCbu())) {
            throw new IllegalArgumentException("Ya existe un destinatario con ese CBU.");
        }
        if (destinatarioRepository.existsByClienteClienteIdAndAlias(cliente.getClienteId(), dto.getAlias())) {
            throw new IllegalArgumentException("Ya existe un destinatario con ese alias.");
        }

        // Mapear DTO a entidad
        Destinatario destinatario = new Destinatario();
        destinatario.setDescripcion(dto.getDescripcion());
        destinatario.setCbu(dto.getCbu());
        destinatario.setAlias(dto.getAlias());
        destinatario.setMoneda(dto.getMoneda());
        destinatario.setCuentaNro(dto.getCuentaNro());
        destinatario.setFechaAlta(LocalDate.now());
        destinatario.setCliente(cliente);

        destinatarioRepository.save(destinatario);
    }

    @Override
    public List<DestinatarioResponseDTO> obtenerDestinatarios(String username) {
        // Buscar cliente por username usando el servicio cacheado
        Cliente cliente = clienteService.findByUsuario(username);

        List<Destinatario> destinatarios = destinatarioRepository.findByClienteClienteId(cliente.getClienteId());

        return destinatarios.stream().map(dest -> {
            DestinatarioResponseDTO dto = new DestinatarioResponseDTO();
            dto.setDestinatarioId(dest.getDestinatarioId());
            dto.setDescripcion(dest.getDescripcion());
            dto.setCbu(dest.getCbu());
            dto.setAlias(dest.getAlias());
            dto.setMoneda(dest.getMoneda());
            dto.setCuentaNro(dest.getCuentaNro());
            dto.setFechaAlta(dest.getFechaAlta());
            return dto;
        }).collect(Collectors.toList());
    }
}
