package com.appbanco.demo.service;

import com.appbanco.demo.dto.MovimientoResponseDTO;
import com.appbanco.demo.entity.Cliente;
import com.appbanco.demo.entity.Movimiento;
import com.appbanco.demo.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ClienteService clienteService; // Inyectamos el servicio para usar la caché


    @Override
    public List<MovimientoResponseDTO> obtenerMovimientosPorCuenta(Long cuentaId, String username) {
        // Verificar que la cuenta pertenezca al cliente autenticado
        // Usamos el servicio cacheado para obtener el cliente
        Cliente cliente = clienteService.findByUsuario(username);

        boolean esPropietario = cliente.getCuentas().stream()
                .anyMatch(cuenta -> cuenta.getCuentaId().equals(cuentaId));

        if (!esPropietario) {
            throw new RuntimeException("No tienes acceso a esta cuenta");
        }

        //Obtener movimientos de la cuenta
        List<Movimiento> movimientos = movimientoRepository.findByCuentaCuentaIdOrderByFechaDesc(cuentaId);

        // mapear a DTOs
        return movimientos.stream().map(movimiento -> {
            MovimientoResponseDTO dto = new MovimientoResponseDTO();
            dto.setMovimientoId(movimiento.getMovimientoId());
            dto.setFecha(movimiento.getFecha());
            dto.setTipoMovimiento(movimiento.getTipoMovimiento());
            dto.setConcepto(movimiento.getConcepto());
            dto.setMonto(movimiento.getMonto());
            dto.setReferencia(movimiento.getReferencia());
            dto.setCuentaId(movimiento.getCuenta().getCuentaId());
            dto.setSaldoResultante(movimiento.getSaldoResultante());
            return dto;
        }).collect(Collectors.toList());
    }
}
