package com.blumbit.CompraVentas2.dto;

import java.math.BigDecimal;

import com.blumbit.CompraVentas2.entity.Producto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductoResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioVentaActual;
    private String marca;
    //imagen 
    private String imagen;
    private String nombreCategoria;

    public static ProductoResponse fromEntity(Producto producto){
        return ProductoResponse.builder()
        .id(producto.getId())
        .nombre(producto.getNombre())
        .build();
    }

}
