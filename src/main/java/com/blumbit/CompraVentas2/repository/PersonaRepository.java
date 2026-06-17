package com.blumbit.CompraVentas2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blumbit.CompraVentas2.entity.Persona;
import com.blumbit.CompraVentas2.entity.Usuario;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    Persona findByUsuario(Usuario usuario);


}
