package com.blumbit.CompraVentas2.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.blumbit.CompraVentas2.dto.ProductoFilterCriteria;
import com.blumbit.CompraVentas2.entity.Producto;

import jakarta.persistence.criteria.Predicate;

public class ProductoSpecification {
    
    public static Specification<Producto> createSpecification(ProductoFilterCriteria criterials, String  filterValue){
        return(root, query, criteariaBuilder)->{
            List<Predicate> predicates = new ArrayList<>();

            if(filterValue != null && !filterValue.translateEscapes().isEmpty()){
                String likeFilterValue= "%" + filterValue.toLowerCase() + "%";
                predicates.add(criteariaBuilder.or(
                    //where nombre like '%{filtervalue} or descripcion or ...'
                    criteariaBuilder.like(criteariaBuilder.lower(root.get("nombre")),filterValue),
                    criteariaBuilder.like(criteariaBuilder.lower(root.get("descripcion")),filterValue),
                    criteariaBuilder.like(criteariaBuilder.lower(root.get("marca")),filterValue),
                    criteariaBuilder.like(criteariaBuilder.lower(root.join("categoria").get("nombre")),filterValue)
                ));
                
            }
            if(criterials.getNombre() != null && !criterials.getNombre().trim().isEmpty()){
                predicates.add(criteariaBuilder.like(criteariaBuilder.lower(root.get("nombre")),
                 "%"+ criterials.getNombre().toLowerCase()+"%"));
            }
             if(criterials.getDescripcion() != null && !criterials.getDescripcion().trim().isEmpty()){
                predicates.add(criteariaBuilder.like(criteariaBuilder.lower(root.get("descripcion")),
                 "%"+ criterials.getDescripcion().toLowerCase()+"%"));
            }
            if(criterials.getMarca() != null && !criterials.getMarca().trim().isEmpty()){
                predicates.add(criteariaBuilder.like(criteariaBuilder.lower(root.get("marca")),
                 "%"+ criterials.getMarca().toLowerCase()+"%"));
            }
            if(criterials.getNombreCategoria() != null && !criterials.getNombreCategoria().trim().isEmpty()){
                predicates.add(criteariaBuilder.like(criteariaBuilder.lower(root.join("categoria").get("nombre")),
                 "%"+ criterials.getNombreCategoria().toLowerCase()+"%"));
            }
            return criteariaBuilder.and(predicates);  
        };
    }
}
