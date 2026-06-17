package com.blumbit.CompraVentas2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.CompraVentas2.entity.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
    Permiso findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
