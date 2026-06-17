package com.blumbit.CompraVentas2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.CompraVentas2.dto.CreateUsuarioDto;
import com.blumbit.CompraVentas2.dto.UsuarioDto;
import com.blumbit.CompraVentas2.service.IUsuarioService;

import jakarta.validation.Valid;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    //1 para utilizar logs
    //el la importacion tanto de Logger como del LoggerFactory(tiene que ser el ORG.SLF4J)
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    //------------------------------------------------//
    //            CRUD SIN EL AUTOWIRED               //
    //------------------------------------------------//
    private final IUsuarioService usuarioService;
    //INYECCION POR COSTRUCTOR 
    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    //LAS ANOTACIONES POR SNIP
    @GetMapping
    public  ResponseEntity<List<UsuarioDto>>  getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UsuarioDto>  GetusuarioById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> createUsuario(@Valid @RequestBody CreateUsuarioDto createUsuarioDto) {
        logger.info("Datos recibidos para creacion del usuario: {}",createUsuarioDto.toString());
        return ResponseEntity.ok(usuarioService.crearUsuario(createUsuarioDto));
    }
    
    
}

