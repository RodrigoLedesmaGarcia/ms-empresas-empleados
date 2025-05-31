package com.spring.www.ms_empresas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class HandlerApiException {

    public static ResponseEntity<Map<String, Object>> errorResponse (HttpStatus status, String message){
        Map<String, Object> response = new HashMap<>();
        response.put("error", message);
        response.put("status", status.value());
        return ResponseEntity.status(status).body(response);
    }

    // 500
    public static ResponseEntity<Map<String, Object>> internal_server_error (String message){
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR,message);
    }
    //404
    public static ResponseEntity<Map<String, Object>> not_found (String message){
        return errorResponse(HttpStatus.NOT_FOUND,message);
    }
    //400
    public static ResponseEntity<Map<String, Object>> bad_request (String message){
        return errorResponse(HttpStatus.BAD_REQUEST,message);
    }

}
