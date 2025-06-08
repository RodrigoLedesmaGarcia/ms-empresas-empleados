package com.spring.www.ms_empresas.controller;

import com.spring.www.ms_empresas.entity.Empresa;
import com.spring.www.ms_empresas.exceptions.HandlerApiException;
import com.spring.www.ms_empresas.service.EmpresaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
public class EmpresasController {

    private final EmpresaServiceImpl service;

    public EmpresasController(EmpresaServiceImpl service) {
        this.service = service;
    }

    /*
      - Este tipo de paginacion es para las pruebas en postman
      - no valido para produccion

     */
    @GetMapping("listar/empresas")
    public ResponseEntity<?> listarEmpresas(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<Empresa> listaEmpresas = service.listarEmpresas();

        if (listaEmpresas.isEmpty()) {
            return HandlerApiException.not_found("no se encontraron elementos para mostrar");
        }

        int start = page * size, end = Math.min(page + size, listaEmpresas.size());

        if (start >= listaEmpresas.size()) {
            return HandlerApiException.bad_request("la pagina selecionada no existe");
        }

        List<Empresa> pages = listaEmpresas.subList(start, end);
        Map<String, Object> response = new HashMap<>();
        response.put("pages", pages);
        response.put("page", page);
        response.put("size", size);
        response.put("total", listaEmpresas.size());
        response.put("totalPages", (int) Math.ceil((double) listaEmpresas.size() / size));
        return ResponseEntity.ok(response); // 201

    } //


    /*
      - end point para buscar una empresa por id
     */

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> buscarEmpresaPorId(@PathVariable Long id) {
        Optional<Empresa> empresaId = service.buscarEmpresaPorId(id);
        if (empresaId.isEmpty()) {
            return HandlerApiException.not_found("no se encontro ninguna empresa con ese id: " + id); // error http 404
        } else {
            return ResponseEntity.ok(empresaId); // exito 200 ok
        }
    } //


    /*
     - end point para crear una empresa nueva
     */

    @PostMapping("/crear/empresa")
    private ResponseEntity<?> nuevaEmpresa(@Valid @RequestBody Empresa empresa, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            result.getFieldErrors().forEach(e -> {
                errors.put(e.getField(), "El campo " + e.getDefaultMessage());
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors); // 400 nad request
        } else {
            try {
                Empresa newEmpresa = service.crearEmpresa(empresa);
                return ResponseEntity.status(HttpStatus.CREATED).body(newEmpresa); // 201 created

            } catch (IllegalArgumentException e) {
                return HandlerApiException.bad_request("no se pudo crear la empresa"); // 400 bad request
            }
        }

    }

    /*
     - end point para editar empresas ya existentes
     */
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarEmpresas(@Valid @RequestBody Empresa empresa, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            result.getFieldErrors().forEach(e -> {
                errors.put(e.getField(), "El campo " + e.getDefaultMessage());
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors); // 400 nad request
        } else {
            try {
                Optional<Empresa> empresaId = service.buscarEmpresaPorId(id);
                if (empresaId.isPresent()) {
                    Empresa empresaUpdate = empresaId.get();
                    empresaUpdate.setNombre(empresa.getNombre());
                    empresaUpdate.setIndustria(empresa.getIndustria());

                    Empresa empresaDB = service.crearEmpresa(empresaUpdate);
                    return ResponseEntity.status(HttpStatus.CREATED).body(empresaDB);
                } else {
                    return HandlerApiException.not_found("no se pudo editar la empresa"); // 404 not found
                }
            } catch (IllegalArgumentException ex) {
                return HandlerApiException.bad_request("no se pudo editar"); // 400 not found
            }
        }

    }//

    /*
     - end point para eliminar una empresa
     */

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminarEmpresa(@PathVariable Long id) {
        Optional<Empresa> eliminarEmpresa = service.buscarEmpresaPorId(id);
        try {
            if (eliminarEmpresa.isPresent()) {
                service.eliminarEmpresa(id);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "message", "empresas eliminado con exito"
                ));
            } else {
                return HandlerApiException.not_found("no se pudo borrar la empresa");
            }
        } catch (IllegalArgumentException e) {
            return HandlerApiException.bad_request("no se pudo borrar la empresa");
        }
    }//

}//
