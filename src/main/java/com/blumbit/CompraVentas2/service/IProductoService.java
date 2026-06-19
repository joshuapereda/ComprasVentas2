package com.blumbit.CompraVentas2.service;

import java.util.List;

import com.blumbit.CompraVentas2.common.dto.PageableRequest;
import com.blumbit.CompraVentas2.common.dto.PageableResponse;
import com.blumbit.CompraVentas2.dto.ProductoFilterCriteria;
import com.blumbit.CompraVentas2.dto.ProductoRequest;
import com.blumbit.CompraVentas2.dto.ProductoResponse;

public interface IProductoService  {
    PageableResponse<ProductoResponse>getProductosPagination(PageableRequest<ProductoFilterCriteria>pageableRequest);
    //Creacion de productos
    List<ProductoResponse> crearProducto(ProductoRequest productoRequest);
    List<ProductoResponse>getProductosByAlmacen(Integer almacenId);
   
}
