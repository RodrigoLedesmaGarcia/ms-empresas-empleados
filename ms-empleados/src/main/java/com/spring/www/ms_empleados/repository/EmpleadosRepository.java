package com.spring.www.ms_empleados.repository;

import com.spring.www.ms_empleados.entity.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadosRepository extends JpaRepository<Empleado, Long> {

    @Query("SELECT e FROM Empleado e")
    Page<Empleado> findAllEmployees(Pageable pageable);

    Optional<Empleado> findEmpleadoByNombreIgnoreCase(String nombre);
}
