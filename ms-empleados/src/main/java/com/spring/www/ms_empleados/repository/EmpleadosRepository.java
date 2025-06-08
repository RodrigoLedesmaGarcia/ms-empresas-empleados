package com.spring.www.ms_empleados.repository;

import com.spring.www.ms_empleados.entity.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadosRepository extends JpaRepository<Empleado, Long> {

    Page<Empleado> findAllEmployees(Pageable pageable);

    Optional<Empleado> findEmpleadoByNombreIgnoreCase(String nombre);
}
