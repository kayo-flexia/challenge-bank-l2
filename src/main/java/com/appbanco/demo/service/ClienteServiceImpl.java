package com.appbanco.demo.service;

import com.appbanco.demo.dto.ClienteRequestDTO;
import com.appbanco.demo.dto.ClienteResponseDTO;
import com.appbanco.demo.dto.CuentaResponseDTO;
import com.appbanco.demo.dto.DireccionDTO;
import com.appbanco.demo.entity.Cliente;
import com.appbanco.demo.entity.Cuenta;
import com.appbanco.demo.entity.Direccion;
import com.appbanco.demo.repository.ClienteRepository;
import com.appbanco.demo.repository.CuentaRepository;
import com.appbanco.demo.utils.GeneradorDatosBancarios;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Random;

@Service //marca esta clase como un componente de servicio de Spring, haciéndola elegible para la inyección de dependencias.
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;
    private final GeneradorDatosBancarios generadorDatosBancarios;
    private final PasswordEncoder passwordEncoder;

    public ClienteServiceImpl(ClienteRepository clienteRepository, CuentaRepository cuentaRepository, GeneradorDatosBancarios generadorDatosBancarios, PasswordEncoder passwordEncoder) { // inyección por constructor
        this.clienteRepository = clienteRepository;
        this.cuentaRepository = cuentaRepository;
        this.generadorDatosBancarios = generadorDatosBancarios;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public ClienteResponseDTO registrarCliente(ClienteRequestDTO dto) {

        Direccion direccion = new Direccion();
        direccion.setCalle(dto.getDireccion().getCalle());
        direccion.setAltura(dto.getDireccion().getAltura());
        direccion.setCiudad(dto.getDireccion().getCiudad());
        direccion.setProvincia(dto.getDireccion().getProvincia());
        direccion.setCodigoPostal(dto.getDireccion().getCodigoPostal());

        if (clienteRepository.existsByUsuario(dto.getUsuario())) {
            throw new IllegalArgumentException("El usuario ya existe. Elija otro nombre de usuario.");
        }

        // Mapear DTO a entidad Cliente
        Cliente cliente = new Cliente();
        cliente.setDireccion(direccion);
        cliente.setCuil(dto.getCuil());
        cliente.setDni(dto.getDni());
        cliente.setSexo(dto.getSexo());
        cliente.setApellido(dto.getApellido());
        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setUsuario(dto.getUsuario());
        cliente.setClave(passwordEncoder.encode(dto.getClave()));
        cliente.setEstado("ACTIVO");
        cliente.setRol("USER");

        // Crear Cuenta automáticamente
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(generadorDatosBancarios.generarNumeroCuenta());
        cuenta.setCbu(generadorDatosBancarios.generarCBU());
        cuenta.setAlias(generadorDatosBancarios.generarAlias());
        cuenta.setTipoCuenta("CAJA_DE_AHORRO");
        cuenta.setMoneda("ARS");
        cuenta.setSaldoActual(0.0);
        cuenta.setEstado("ACTIVA");
        cuenta.setCliente(cliente);

        // Relacionar cuenta con cliente
        cliente.setCuentas(Collections.singletonList(cuenta));

        // Persistir Cliente (y Cuenta por cascade). Se guarda el cliente en la base de datos. JPA traduce tu objeto Cliente a una sentencia SQL INSERT
        clienteRepository.save(cliente);
        //recordemos que tenemos @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL) private List<Cuenta> cuentas; en la entidad clientes.
        //gracias a cascade = CascadeType.ALL, no necesitas llamar a cuentaRepository.save(cuenta);

        // Mapear ClienteResponseDTO
        ClienteResponseDTO responseDTO = new ClienteResponseDTO();

        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setCalle(cliente.getDireccion().getCalle());
        direccionDTO.setAltura(cliente.getDireccion().getAltura());
        direccionDTO.setCiudad(cliente.getDireccion().getCiudad());
        direccionDTO.setProvincia(cliente.getDireccion().getProvincia());
        direccionDTO.setCodigoPostal(cliente.getDireccion().getCodigoPostal());
        responseDTO.setDireccion(direccionDTO);

        responseDTO.setClienteId(cliente.getClienteId());
        responseDTO.setNombre(cliente.getNombre());
        responseDTO.setApellido(cliente.getApellido());
        responseDTO.setEmail(cliente.getEmail());
        responseDTO.setTelefono(cliente.getTelefono());
        responseDTO.setUsuario(cliente.getUsuario());
        responseDTO.setEstado(cliente.getEstado());

        CuentaResponseDTO cuentaResponseDTO = new CuentaResponseDTO();
        cuentaResponseDTO.setCuentaId(cuenta.getCuentaId());
        cuentaResponseDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaResponseDTO.setCbu(cuenta.getCbu());
        cuentaResponseDTO.setAlias(cuenta.getAlias());
        cuentaResponseDTO.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaResponseDTO.setMoneda(cuenta.getMoneda());
        cuentaResponseDTO.setSaldoActual(cuenta.getSaldoActual());
        cuentaResponseDTO.setEstado(cuenta.getEstado());

        responseDTO.setCuentas(Collections.singletonList(cuentaResponseDTO));

        return responseDTO;
    }
}
