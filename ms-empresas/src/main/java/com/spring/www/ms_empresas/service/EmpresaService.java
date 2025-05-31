package com.spring.www.ms_empresas.service;

import com.spring.www.ms_empresas.entity.Empresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaService {

    List<Empresa> listarEmpresas();

    Optional<Empresa> buscarEmpresaPorId(Long id);

    Optional<Empresa> buscarEmpresaPorNombre(String nombre);

    Empresa crearEmpresa(Empresa empresa);

    void eliminarEmpresa(Long id);


}
