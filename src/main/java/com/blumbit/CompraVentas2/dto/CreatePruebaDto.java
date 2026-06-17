package com.blumbit.CompraVentas2.dto;

import com.blumbit.CompraVentas2.entity.Prueba;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor 
public class CreatePruebaDto {
    private String nombre;
    private String apellido;
    private String descripcion;

    //Metodo para convertir de DTO a entidad
    public static Prueba toEntity(CreatePruebaDto createPrueba) {
        Prueba prueba = new Prueba();
        prueba.setNombre(createPrueba.getNombre());
        prueba.setApellido(createPrueba.getApellido());
        prueba.setDescripcion(createPrueba.getDescripcion());
        return prueba;
    } 

}
