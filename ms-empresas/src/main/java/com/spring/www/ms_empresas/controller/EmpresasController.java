package com.spring.www.ms_empresas.controller;

import com.spring.www.ms_empresas.entity.Empresa;
import com.spring.www.ms_empresas.exceptions.HandlerApiException;
import com.spring.www.ms_empresas.service.EmpresaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empresas")
public class EmpresasController {

    private final EmpresaServiceImpl service;

    public EmpresasController(EmpresaServiceImpl service) {
        this.service = service;
    }

    @GetMapping("listar/empresas")
    public ResponseEntity<?> listarEmpresas (@RequestParam (defaultValue = "0") int page, @RequestParam (defaultValue = "10" ) int size){
        List<Empresa> listaEmpresas = service.listarEmpresas();

        if (listaEmpresas.isEmpty()){
            return HandlerApiException.not_found("no se encontraron elementos para mostrar");
        }

        int start = page * size, end = Math.min(page + size, listaEmpresas.size());

        if (start >= listaEmpresas.size()){
            return HandlerApiException.bad_request("la pagina selecionada no existe");
        }

        List<Empresa> pages = listaEmpresas.subList(start, end);
        Map<String, Object> response = new HashMap<>();
        response.put("pages", pages);
        response.put("page", page);
        response.put("size", size);
        response.put("total", listaEmpresas.size());
        response.put("totalPages", (int) Math.ceil( (double) listaEmpresas.size() / size));
        return ResponseEntity.ok(response); // 201

    } //

} //
