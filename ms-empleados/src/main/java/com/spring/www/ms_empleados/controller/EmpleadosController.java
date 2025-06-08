package com.spring.www.ms_empleados.controller;

import com.spring.www.ms_empleados.client.EmpresaClient;
import com.spring.www.ms_empleados.entity.Empleado;
import com.spring.www.ms_empleados.exception.HandlerApiRequestException;
import com.spring.www.ms_empleados.pojo.Empresa;
import com.spring.www.ms_empleados.service.EmpleadoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/empleados")
public class EmpleadosController {

    private final EmpleadoServiceImpl empleadoService;

    private final EmpresaClient empresaClient;

    public EmpleadosController(EmpleadoServiceImpl empleadoService, EmpresaClient empresaClient) {
        this.empleadoService = empleadoService;
        this.empresaClient = empresaClient;
    }

    /*
     - end point para listar a todas las empresas
     */
    @GetMapping("/empleados-list")
    public Page<Empleado> listarEmpleadosPaginados(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamanio
    ) {
        return empleadoService.listarEmpleadosPaginados(pagina, tamanio);
    }//

    /*
    - end point para buscar a una empresa por su id
     */
    @GetMapping("/empresa/{id}")
    public Empresa obtenerEmpresaPorId(@PathVariable Long id) {
        Empresa empresaId = empresaClient.buscarEmpresaPorId(id);
        if (empresaId != null){
            return empresaId;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no se encontro ninguna empresa con ese id: "+id);
        }
    } //

    /*
     - end point para buscar empleado por id
     */
    @GetMapping("/buscar-por-id/{id}")
    public ResponseEntity<?> buscarEmpleadoPorId (@PathVariable Long id) {
        Optional<Empleado> empresasId = empleadoService.buscarEmpleadoPorId(id);
        if (empresasId.isPresent()){
            return ResponseEntity.ok(empresasId.get());
        } else {
            return HandlerApiRequestException.notFound("no se encontro ningun empleado con ese id: "+id);
        }
    } //

    @GetMapping("buscar-por-nombre")
    public ResponseEntity<?> buscarPorNombre (@RequestParam String nombre) {
        Optional<Empleado> buscarEmpleadoPorNombre = empleadoService.buscarEmpleadoPorNombre(nombre);
        if (buscarEmpleadoPorNombre.isPresent()){
            return ResponseEntity.ok(buscarEmpleadoPorNombre.get());
        } else {
            return HandlerApiRequestException.notFound("no se encontro a ningun empleado con ese nombre: "+nombre);
        }
    } //

    /*
     - end pont para crear a un empleado
     */
    @PostMapping("crear/empleado")
    public ResponseEntity<?> crearEmpleado (@Valid @RequestBody Empleado empleado, BindingResult result) {
        if (result.hasErrors()){
            Map<String, Object> errors = new HashMap<>();
            result.getFieldErrors().forEach( e -> {
                errors.put(e.getField(), "el campo "+e.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            empleadoService.crearEmpleado(empleado);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "error", "se creo el empleado exitosamente"
            ));
        } catch (IllegalArgumentException ex) {
            return HandlerApiRequestException.badRequest("no se pudo crear al usuario");
        }
    } //

    /*
     - end point para editar un empleado ya existente
     */

    @PutMapping("editar/empleado/{id}")
    public ResponseEntity<?> editarEmpleado (@Valid @RequestBody Empleado empleado, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()){
            Map<String, Object> errors = new HashMap<>();
            result.getFieldErrors().forEach( e-> {
                errors.put(e.getField(), "el campo "+e.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }
        Optional<Empleado> empleadoId = empleadoService.buscarEmpleadoPorId(id);
        try {
            Empleado newEmpledo = empleadoId.get();
            newEmpledo.setNombre(empleado.getNombre());
            newEmpledo.setPuesto(empleado.getPuesto());

            Empleado empleadoBd = empleadoService.crearEmpleado(empleado);
            return ResponseEntity.ok(empleadoBd);
        } catch (IllegalArgumentException ex){
            return HandlerApiRequestException.badRequest("no se editar al empledo cinel id: "+id);
        }
    } //

    /*
     -end point para eliminar un empleado
     */
    @DeleteMapping("eliminar-empleado/{id}")
    public ResponseEntity<?> eliminarEmpleado (@PathVariable Long id) {
        Optional<Empleado> empledoId = empleadoService.buscarEmpleadoPorId(id);
        if (empledoId.isPresent()){
            empleadoService.eliminarEmpleado(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "message", "empleado eliminado con exito"
            ));
        } else {
            return HandlerApiRequestException.notFound("no se pudo eliminar al empledo con el id: "+id+" ya que no existe");
        }
    }

}//
