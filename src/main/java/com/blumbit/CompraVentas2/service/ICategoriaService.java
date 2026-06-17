package com.blumbit.CompraVentas2.service;

import java.util.List;

import com.blumbit.CompraVentas2.dto.CategoriaDto;

public interface ICategoriaService {
    // Define los métodos que el servicio de Categoria debe implementar
    //la logica de negocio se implementa en la clase CategoriaService, 
    // que implementa esta interfaz    
    List<CategoriaDto> listarCategorias();
    CategoriaDto obtenerCategoriaPorId(Integer id);
    CategoriaDto crearCategoria(CategoriaDto categoria);
    CategoriaDto actualizarCategoria(Integer id, CategoriaDto categoriaActualizada);
    void eliminarCategoria(Integer id);
}
