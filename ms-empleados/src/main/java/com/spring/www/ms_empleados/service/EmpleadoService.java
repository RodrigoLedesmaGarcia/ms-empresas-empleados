package com.spring.www.ms_empleados.service;


import com.spring.www.ms_empleados.entity.Empleado;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface EmpleadoService {

     Page<Empleado> listarEmpleadosPaginados(int pagina, int tamanio);

     Optional<Empleado> buscarEmpleadoPorId(Long id);

     Optional<Empleado> buscarEmpleadoPorNombre(String nombre);

     Empleado crearEmpleado (Empleado empleado);

     void eliminarEmpleado(Long id);


}
