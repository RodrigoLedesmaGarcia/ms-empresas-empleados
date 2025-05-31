package com.spring.www.ms_empresas.service;

import com.spring.www.ms_empresas.entity.Empresa;
import com.spring.www.ms_empresas.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository repository;

    public EmpresaServiceImpl(EmpresaRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Empresa> listarEmpresas() {
        return (List<Empresa>) repository.findAll();
    }

    @Override
    public Optional<Empresa> buscarEmpresaPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Empresa> buscarEmpresaPorNombre(String nombre) {
        return repository.findEmpresaByNombreIgnoreCase(nombre);
    }

    @Override
    public Empresa crearEmpresa(Empresa empresa) {
        return repository.save(empresa);
    }

    @Override
    public void eliminarEmpresa(Long id) {
           repository.deleteById(id);
    }
}
