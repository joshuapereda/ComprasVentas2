package com.blumbit.CompraVentas2.dto;

import com.blumbit.CompraVentas2.entity.Categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
//Defiine que estamos trabajando con DTO
@Builder
public class CreateCategoriaDto {
    private String nombre;
    private String descripcion;

    public static Categoria toEntity(CreateCategoriaDto  createCategoriaDto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(createCategoriaDto.getNombre());
        categoria.setDescripcion(createCategoriaDto.getDescripcion());
        return categoria;
    }

}
