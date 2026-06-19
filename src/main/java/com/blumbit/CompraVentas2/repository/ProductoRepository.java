package com.blumbit.CompraVentas2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.blumbit.CompraVentas2.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Integer>, JpaSpecificationExecutor<Producto>{

}
