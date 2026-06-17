package com.blumbit.CompraVentas2.dto;

import com.blumbit.CompraVentas2.entity.Permiso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreatePermisoDto {
    @NotBlank(message = "El nombre del permiso no puede estar vacío")
    @Size(max = 100, message = "El nombre del permiso no puede tener más de 100 caracteres")
    private String nombre;
    
    @Size(max = 100, message = "El recurso no puede tener más de 100 caracteres")
    private String recurso;
    
    @NotBlank(message = "La acción no puede estar vacía")
    @Size(max = 100, message = "La acción no puede tener más de 100 caracteres")
    private String action;
    
    public static Permiso toEntity(CreatePermisoDto createPermisoDto) {
        return Permiso.builder()
                .nombre(createPermisoDto.getNombre())
                .recurso(createPermisoDto.getRecurso())
                .action(createPermisoDto.getAction())
                .build();
    }
}
