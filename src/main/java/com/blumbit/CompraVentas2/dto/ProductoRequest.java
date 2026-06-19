package com.blumbit.CompraVentas2.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoRequest {
    private String nombre;
    private String descripcion;
    private BigDecimal precioVentaActual;
    private String marca;
    //imagen 
    private MultipartFile file;
    private String categoriaId;

}
