package com.spring.www.ms_empleados.service;

import com.spring.www.ms_empleados.client.EmpresaClient;
import com.spring.www.ms_empleados.entity.Empleado;
import com.spring.www.ms_empleados.repository.EmpleadosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {


    private final EmpresaClient empresaClient;

    private final EmpleadosRepository empleadosRepository;

    public EmpleadoServiceImpl(EmpresaClient empresaClient, EmpleadosRepository empleadosRepository) {
        this.empresaClient = empresaClient;
        this.empleadosRepository = empleadosRepository;
    }

    @Override
    public Page<Empleado> listarEmpleadosPaginados(int pagina, int tamanio) {
        return empleadosRepository.findAllEmployees(PageRequest.of(pagina, tamanio));
    }

    @Override
    public Optional<Empleado> buscarEmpleadoPorId(Long id) {
        return empleadosRepository.findById(id);
    }

    @Override
    public Optional<Empleado> buscarEmpleadoPorNombre(String nombre) {
        return empleadosRepository.findEmpleadoByNombreIgnoreCase(nombre);
    }

    @Override
    public Empleado crearEmpleado(Empleado empleado) {
        return empleadosRepository.save(empleado);
    }

    @Override
    public void eliminarEmpleado(Long id) {
       empleadosRepository.deleteById(id);
    }
}
