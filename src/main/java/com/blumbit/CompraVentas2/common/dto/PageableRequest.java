package com.blumbit.CompraVentas2.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableRequest <T> {
    private int pageSize;
    private int pageNumber;
    private String sortField;
    private String sortOrder;
    private T criterials;
    private String filterValue;

    

}
