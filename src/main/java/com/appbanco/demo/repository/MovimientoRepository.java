package com.appbanco.demo.repository;

import com.appbanco.demo.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    // Obtener movimientos de una cuenta ordenados por fecha descendente
    List<Movimiento> findByCuentaCuentaIdOrderByFechaDesc(Long cuentaId);
}
