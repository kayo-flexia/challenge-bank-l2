package com.appbanco.demo.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class GeneradorDatosBancarios {

    private final Random random = new Random();

    public String generarNumeroCuenta() {
        // Genera un número de cuenta de 10 dígitos (formato simple)
        return String.format("%010d", random.nextInt(1_000_000_000));
    }

    public String generarCBU() {
        // Genera un CBU de 22 dígitos (simplificado, no válido en la vida real)
        long parte1 = Math.abs(random.nextLong() % 1_000_000_0000L);
        long parte2 = Math.abs(random.nextLong() % 1_000_000_0000000L);
        return String.format("%010d%012d", parte1, parte2);
    }

    public String generarAlias() {
        // Alias simple basado en UUID (primeros 12 caracteres)
        return "alias." + UUID.randomUUID().toString().substring(0, 12);
    }
}
