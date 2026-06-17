package com.blumbit.CompraVentas2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blumbit.CompraVentas2.dto.CategoriaDto;
import com.blumbit.CompraVentas2.dto.CreateCategoriaDto;
import com.blumbit.CompraVentas2.entity.Categoria;
import com.blumbit.CompraVentas2.repository.CategoriaRepository;

@Service
public class CategoriaService {
    // Inyección de dependencias del repositorio
    // El repositorio se utiliza para acceder a los datos de la entidad Categoria
   @Autowired
    private  CategoriaRepository categoriaRepository;
    //Definir las funciones del CRUD
    public List<CategoriaDto>listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaDto::fromEntity)
                .collect(java.util.stream.Collectors.toList());
    }
    public CategoriaDto obtenerCategoriaPorId(Integer id) {
        return CategoriaDto.fromEntity(categoriaRepository.findById(id).get());
    }
    public CategoriaDto crearCategoria(CreateCategoriaDto categoria) {
        Categoria nuevaCategoria = CreateCategoriaDto.toEntity(categoria);
        return CategoriaDto.fromEntity(categoriaRepository.save(nuevaCategoria));
       
    }
    public CategoriaDto actualizarCategoria(Integer id, Categoria categoriaActualizada) {
        Categoria categoriaExistente = categoriaRepository.findById(id).get();
        categoriaExistente.setNombre(categoriaActualizada.getNombre());
        categoriaExistente.setDescripcion(categoriaActualizada.getDescripcion());
        return CategoriaDto.fromEntity(categoriaRepository.save(categoriaExistente));
    }
    public void eliminarCategoria(Integer id) {
        categoriaRepository.deleteById(id);
    }
    
    //Definir las funciones del CRUD
    /*public List<Categoria>listarCategorias() {
        return categoriaRepository.findAll();
    }
    public Categoria obtenerCategoriaPorId(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }
    public Categoria crearCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    public Categoria actualizarCategoria(Integer id, Categoria categoriaActualizada) {
        Categoria categoriaExistente = categoriaRepository.findById(id).orElse(null);
        if (categoriaExistente != null) {
            categoriaExistente.setNombre(categoriaActualizada.getNombre());
            categoriaExistente.setDescripcion(categoriaActualizada.getDescripcion());
            return categoriaRepository.save(categoriaExistente);
        }
        return null;
    }
    public void eliminarCategoria(Integer id) {
        categoriaRepository.deleteById(id);
    }*/

}
