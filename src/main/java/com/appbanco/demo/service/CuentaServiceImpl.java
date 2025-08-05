package com.appbanco.demo.service;

import com.appbanco.demo.dto.CuentaResponseDTO;
import com.appbanco.demo.entity.Cliente;
import com.appbanco.demo.entity.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private ClienteService clienteService;

    @Override
    public List<CuentaResponseDTO> obtenerCuentasPorUsuario(String username) {
        // Buscar cliente por username usando el servicio cacheado
        Cliente cliente = clienteService.findByUsuario(username);

        // Obtener sus cuentas asociadas
        List<Cuenta> cuentas = cliente.getCuentas();

        // Mapear a CuentaResponseDTO
        return cuentas.stream().map(cuenta -> {
            CuentaResponseDTO dto = new CuentaResponseDTO();
            dto.setCuentaId(cuenta.getCuentaId());
            dto.setNumeroCuenta(cuenta.getNumeroCuenta());
            dto.setCbu(cuenta.getCbu());
            dto.setAlias(cuenta.getAlias());
            dto.setTipoCuenta(cuenta.getTipoCuenta());
            dto.setMoneda(cuenta.getMoneda());
            dto.setSaldoActual(cuenta.getSaldoActual());
            dto.setEstado(cuenta.getEstado());
            return dto;
        }).collect(Collectors.toList());
    }
}
