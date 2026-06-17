package com.blumbit.CompraVentas2.dto;

import com.blumbit.CompraVentas2.entity.Categoria;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//Defiine que estamos trabajando con DTO
@Builder
public class CategoriaDto {
    private Integer id;
    private String nombre;
    private String descripcion;
    //Metodo para convertir de entidad a DTO
     public static CategoriaDto fromEntity(Categoria categoria) {
        return CategoriaDto.builder()
        .id(categoria.getId())
        .nombre(categoria.getNombre())
        .descripcion(categoria.getDescripcion())
        .build();
    }

}
