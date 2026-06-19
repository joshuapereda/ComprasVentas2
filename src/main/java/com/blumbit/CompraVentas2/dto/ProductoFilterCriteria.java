package com.blumbit.CompraVentas2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoFilterCriteria {
    private String nombre;
    private String descripcion;
    private String codigoBarra;
    private String  marca;
    private String nombreCategoria;

}
