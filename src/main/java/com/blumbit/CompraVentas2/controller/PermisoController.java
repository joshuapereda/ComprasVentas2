package com.blumbit.CompraVentas2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.CompraVentas2.dto.CreatePermisoDto;
import com.blumbit.CompraVentas2.dto.PermisoDto;
import com.blumbit.CompraVentas2.service.IPermisoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/permisos")
public class PermisoController {
    private static final Logger logger = LoggerFactory.getLogger(PermisoController.class);

    @Autowired
    private final IPermisoService permisoService;

    public PermisoController(IPermisoService permisoService) {
        this.permisoService = permisoService;
    }

    @GetMapping
    public ResponseEntity<List<PermisoDto>> getAllPermisos() {
        return ResponseEntity.ok(permisoService.listarPermisos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermisoDto> getPermisoById(@PathVariable Integer id) {
        return ResponseEntity.ok(permisoService.obtenerPermisoPorId(id));
    }

    @PostMapping
    public ResponseEntity<PermisoDto> createPermiso(@Valid @RequestBody CreatePermisoDto createPermisoDto) {
        logger.info("Datos recibidos para creación del permiso: {}", createPermisoDto.toString());
        return ResponseEntity.ok(permisoService.crearPermiso(createPermisoDto));
    }
}
