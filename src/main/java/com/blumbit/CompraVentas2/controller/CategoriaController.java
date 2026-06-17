package com.blumbit.CompraVentas2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.CompraVentas2.dto.CategoriaDto;
import com.blumbit.CompraVentas2.dto.CreateCategoriaDto;
import com.blumbit.CompraVentas2.entity.Categoria;
import com.blumbit.CompraVentas2.service.CategoriaService;

import lombok.Data;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    // Inyección de dependencias con el AUTOWIRED del servicio
    // El servicio se utiliza para manejar la lógica de negocio
    // relacionada con la entidad Categoria        
    @Autowired
    private  CategoriaService categoriaServicee;

    @GetMapping 
    public List<CategoriaDto> listarCategorias() {
        return categoriaServicee.listarCategorias();
    }
    @GetMapping("/{id}")
    public CategoriaDto buscarCategoriaPorId(@PathVariable Integer id) {
        return categoriaServicee.obtenerCategoriaPorId(id);
    }
    @PostMapping
    public CategoriaDto crearCategoria( @RequestBody CreateCategoriaDto categoria) {
        return categoriaServicee.crearCategoria(categoria);
    }
    @PutMapping("/{id}")
    public CategoriaDto actualizarCategoria(@PathVariable Integer id, @RequestBody Categoria categoriaActualizada) {
        return categoriaServicee.actualizarCategoria(id, categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoria(@PathVariable Integer id) {
        categoriaServicee.eliminarCategoria(id);
    }
}
