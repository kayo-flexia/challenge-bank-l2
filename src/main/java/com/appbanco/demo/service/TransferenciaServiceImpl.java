package com.appbanco.demo.service;

import com.appbanco.demo.dto.TransferenciaRequestDTO;
import com.appbanco.demo.dto.TransferenciaResponseDTO;
import com.appbanco.demo.entity.*;
import com.appbanco.demo.enums.ConceptoMovimiento;
import com.appbanco.demo.enums.TipoMovimiento;
import com.appbanco.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // Lombok para inyección de dependencias por constructor
public class TransferenciaServiceImpl implements TransferenciaService {

    private final ClienteService clienteService; // Inyectamos el servicio para usar la caché
    private final CuentaRepository cuentaRepository;
    private final DestinatarioRepository destinatarioRepository;
    private final TransferenciaRepository transferenciaRepository;
    private final MovimientoRepository movimientoRepository;

    @Override
    @Transactional
    public TransferenciaResponseDTO realizarTransferencia(TransferenciaRequestDTO dto, String username) {
        // Obtener Cliente autenticado usando el servicio cacheado
        Cliente cliente = clienteService.findByUsuario(username);

        // Validar que la cuenta origen pertenece al cliente autenticado
        Cuenta cuentaOrigen = cuentaRepository.findById(dto.getCuentaOrigenId())
                .orElseThrow(() -> new RuntimeException("Cuenta origen no encontrada"));

        if (!cuentaOrigen.getCliente().getClienteId().equals(cliente.getClienteId())) {
            throw new SecurityException("No tienes permiso para operar sobre esta cuenta");
        }

        // Validar que el destinatario esté en la agenda del cliente
        Destinatario destinatario = destinatarioRepository.findById(dto.getDestinatarioId())
                .orElseThrow(() -> new RuntimeException("Destinatario no encontrado"));

        if (!destinatario.getCliente().getClienteId().equals(cliente.getClienteId())) {
            throw new SecurityException("No tienes permiso para transferir a este destinatario");
        }

        //  Validar saldo suficiente
        if (cuentaOrigen.getSaldoActual() < dto.getMonto()) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la transferencia");
        }

        // jecutar transferencia (registrar transferencia, actualizar saldo, registrar movimiento)
        cuentaOrigen.setSaldoActual(cuentaOrigen.getSaldoActual() - dto.getMonto());

        Transferencia transferencia = new Transferencia();
        transferencia.setFecha(LocalDateTime.now());
        transferencia.setCuentaOrigen(cuentaOrigen);
        transferencia.setCuentaDestino(destinatario);
        transferencia.setMonto(dto.getMonto());
        transferencia.setDescripcion(dto.getDescripcion());
        transferencia.setEstado("COMPLETADA");

        transferenciaRepository.save(transferencia);

        // 6. Registrar movimiento asociado
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipoMovimiento(TipoMovimiento.DEBITO);
        movimiento.setConcepto(ConceptoMovimiento.TRANSFERENCIA_SALIENTE);
        movimiento.setMonto(dto.getMonto());
        movimiento.setReferencia("Transferencia a " + destinatario.getAlias());
        movimiento.setCuenta(cuentaOrigen);
        movimiento.setSaldoResultante(cuentaOrigen.getSaldoActual());

        movimientoRepository.save(movimiento);

        // 7. Retornar comprobante (TransferenciaResponseDTO)
        TransferenciaResponseDTO responseDTO = new TransferenciaResponseDTO();
        responseDTO.setTransferenciaId(transferencia.getTransferenciaId());
        responseDTO.setFecha(transferencia.getFecha());
        responseDTO.setMonto(transferencia.getMonto());
        responseDTO.setDescripcion(transferencia.getDescripcion());
        responseDTO.setCuentaOrigenAlias(cuentaOrigen.getAlias());
        responseDTO.setCuentaDestinoCbu(destinatario.getCbu());
        responseDTO.setDestinatarioAlias(destinatario.getDescripcion()); // Usamos la descripción para el comprobante
        responseDTO.setSaldoResultante(cuentaOrigen.getSaldoActual());
        responseDTO.setEstado(cuentaOrigen.getEstado());

        return responseDTO;
    }

    @Override
    public TransferenciaResponseDTO obtenerComprobante(Long transferenciaId, String username) {
        // Obtenemos el cliente usando el servicio cacheado, no el repositorio directamente
        Cliente cliente = clienteService.findByUsuario(username);

        Transferencia transferencia = transferenciaRepository.findById(transferenciaId)
                .orElseThrow(() -> new RuntimeException("Transferencia no encontrada"));

        // La validación sigue siendo la misma, pero ahora el objeto 'cliente' viene de la caché
        if (!transferencia.getCuentaOrigen().getCliente().getClienteId().equals(cliente.getClienteId())) {
            throw new SecurityException("No tienes permiso para ver este comprobante");
        }

        TransferenciaResponseDTO responseDTO = new TransferenciaResponseDTO();
        responseDTO.setTransferenciaId(transferencia.getTransferenciaId());
        responseDTO.setFecha(transferencia.getFecha());
        responseDTO.setMonto(transferencia.getMonto());
        responseDTO.setDescripcion(transferencia.getDescripcion());
        responseDTO.setCuentaOrigenAlias(transferencia.getCuentaOrigen().getAlias());
        responseDTO.setDestinatarioAlias(transferencia.getCuentaDestino().getAlias());
        responseDTO.setCuentaDestinoCbu(transferencia.getCuentaDestino().getCbu());
        responseDTO.setSaldoResultante(transferencia.getCuentaOrigen().getSaldoActual());

        return responseDTO;
    }

}
