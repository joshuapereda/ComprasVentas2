package com.blumbit.CompraVentas2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.CompraVentas2.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
