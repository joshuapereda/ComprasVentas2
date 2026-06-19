package com.blumbit.CompraVentas2.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
//CON <T> INDICA QUE ES UNA CLASE GENERICA
public class PageableResponse<T>{
   
    private List<T> content;
    //METADATOS
    private int pageNumber;
    private int pageSize;
    private int totalElementos;
    private int totalPages;     


}
