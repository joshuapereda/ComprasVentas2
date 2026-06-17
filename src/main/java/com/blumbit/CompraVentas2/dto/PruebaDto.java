package com.blumbit.CompraVentas2.dto;

import com.blumbit.CompraVentas2.entity.Prueba;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PruebaDto {
    private Integer Id;
    private String nombre;
    private String apellido;
    private String descripcion;

    //Metodo para convertir de entidad a DTO
    public static PruebaDto fromEntity(Prueba prueba) {
        return PruebaDto.builder()
        .Id(prueba.getId())
        .nombre(prueba.getNombre())
        .apellido(prueba.getApellido())
        .descripcion(prueba.getDescripcion())
        .build();
    }

}
