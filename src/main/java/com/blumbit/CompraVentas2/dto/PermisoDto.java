package com.blumbit.CompraVentas2.dto;

import com.blumbit.CompraVentas2.entity.Permiso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermisoDto {
    private Integer id;
    private String nombre;
    private String recurso;
    private String action;
    
    public static PermisoDto fromEntity(Permiso permiso) {
        return PermisoDto.builder()
                .id(permiso.getId())
                .nombre(permiso.getNombre())
                .recurso(permiso.getRecurso())
                .action(permiso.getAction())
                .build();
    }
}
