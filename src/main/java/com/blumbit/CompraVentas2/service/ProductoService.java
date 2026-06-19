package com.blumbit.CompraVentas2.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.blumbit.CompraVentas2.common.dto.PageableRequest;
import com.blumbit.CompraVentas2.common.dto.PageableResponse;
import com.blumbit.CompraVentas2.dto.ProductoFilterCriteria;
import com.blumbit.CompraVentas2.dto.ProductoRequest;
import com.blumbit.CompraVentas2.dto.ProductoResponse;
import com.blumbit.CompraVentas2.entity.Producto;
import com.blumbit.CompraVentas2.repository.CategoriaRepository;
import com.blumbit.CompraVentas2.repository.ProductoRepository;
import com.blumbit.CompraVentas2.repository.specification.ProductoSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService implements IProductoService {
    private final ProductoRepository productoRepository;

    private final CategoriaRepository categoriaRepository;

    @Override
    public PageableResponse<ProductoResponse> getProductosPagination(
            PageableRequest<ProductoFilterCriteria> pageableRequest) {
        try {
            //las importaciones son de domain
            Sort sort=pageableRequest.getSortOrder().equalsIgnoreCase("asc")
            ? Sort.by(pageableRequest.getSortField()).ascending()
            : Sort.by(pageableRequest.getSortField()).descending();

            Pageable pageable = PageRequest.of(pageableRequest.getPageNumber(), pageableRequest.getPageSize(), sort);

            Specification<Producto>specification =null;
            if(pageableRequest.getCriterials() !=null){
                specification = ProductoSpecification.createSpecification(pageableRequest.getCriterials(), pageableRequest.getFilterValue());
            }
            Page<Producto> productoPage= productoRepository.findAll(specification,pageable)
            return PageableResponse.<ProductoResponse>builder()
            .content(productoPage.getContent().stream().map(ProductoResponse:: fromEntity).toList())
            .pageNumber(productoPage.getNumber())
            .pageSize(productoPage.getSize())
            .totalElementos(productoPage.getTotalPages())
            .build();


        } catch (Exception e) {
           throw new RuntimeException("error paginacion peoductos ");
        }
    }

    @Override
    public List<ProductoResponse> crearProducto(ProductoRequest productoRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crearProducto'");
    }

    @Override
    public List<ProductoResponse> getProductosByAlmacen(Integer almacenId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductosByAlmacen'");
    }

}
