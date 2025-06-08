package com.spring.www.ms_empleados.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class HandlerApiRequestException {

    public static ResponseEntity<Map<String, Object>> errorResponse (HttpStatus status, String message){
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("stutus", status.value());

        return ResponseEntity.status(status).body(response);
    }

    // error http 500 internal server error
    public static ResponseEntity<Map<String, Object>> internalServerError (String message) {
        return errorResponse (HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    // error http 404 not found
    public static ResponseEntity<Map<String, Object>> notFound (String message){
        return errorResponse (HttpStatus.NOT_FOUND, message);
    }


    // error htto 400 bad request
    public static ResponseEntity<Map<String, Object>> badRequest (String message){
        return errorResponse (HttpStatus.BAD_REQUEST, message);
    }


}
