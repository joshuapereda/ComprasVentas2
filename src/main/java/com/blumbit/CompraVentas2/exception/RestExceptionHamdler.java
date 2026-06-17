package com.blumbit.CompraVentas2.exception;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blumbit.CompraVentas2.common.CustomErrorResponse;
import com.blumbit.CompraVentas2.controller.UsuarioController;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHamdler {

    //1 para utilizar logs
    //el la importacion tanto de Logger como del LoggerFactory(tiene que ser el ORG.SLF4J)
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @ExceptionHandler(DomainExeption.class)
    public ResponseEntity<CustomErrorResponse<String>> handleDomainException(DomainExeption  x, HttpServletRequest req){
        //logear mensaje de error
        logger.error("MethodArgumentNotValidException-GEH: {} ",x.getMessage(),x);
        return new ResponseEntity<>(CustomErrorResponse.<String>builder()
        .statusCode(x.getStatusCode())
        .errorCode(x.getErrorCode())
        .message(x.getMessage())
        //tiene que importar de Data java util
        .timestamp(new Date().toString())
        .path(req.getRequestURI())
        .build(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    //MANEJAR ERRORES DE LAS VALIDACIONES 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponse<List<String>>> handleValidationException(MethodArgumentNotValidException x, HttpServletRequest req){
        List<String>errors=x.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField()+ ": "+error.getDefaultMessage())
        .collect(Collectors.toList());
        return new ResponseEntity<>(CustomErrorResponse.<List<String>>builder()
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.name())
        .message(errors)
        //tiene que importar de Data java util
        .timestamp(new Date().toString())
        .path(req.getRequestURI())
        .build(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR); 
        }

}
