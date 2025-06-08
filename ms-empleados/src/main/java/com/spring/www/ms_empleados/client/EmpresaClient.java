package com.spring.www.ms_empleados.client;

import com.spring.www.ms_empleados.pojo.Empresa;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-empresas", url = "http://localhost:9000/api/empresas/")
public interface EmpresaClient {

    @GetMapping("buscar/{id}")
    Empresa buscarEmpresaPorId(@PathVariable Long id);
}
