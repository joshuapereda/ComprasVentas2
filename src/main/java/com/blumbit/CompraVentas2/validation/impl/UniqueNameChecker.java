package com.blumbit.CompraVentas2.validation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blumbit.CompraVentas2.repository.UsuarioRepository;
import com.blumbit.CompraVentas2.validation.IUniqueNameChecker;

@Service
public class UniqueNameChecker implements IUniqueNameChecker {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean isUniqueName(String name) {
        //voy a buscar por nombre si existe devuelvo true 
        //si no existe devuelvo false 
        return !usuarioRepository.existsByNombre(name); 
    }
}
