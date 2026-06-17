package com.blumbit.CompraVentas2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blumbit.CompraVentas2.dto.CreatePermisoDto;
import com.blumbit.CompraVentas2.dto.PermisoDto;
import com.blumbit.CompraVentas2.entity.Permiso;
import com.blumbit.CompraVentas2.exception.ResourceNotFoundException;
import com.blumbit.CompraVentas2.repository.PermisoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PermisoService implements IPermisoService {
    @Autowired
    private PermisoRepository permisoRepository;

    @Override
    public List<PermisoDto> listarPermisos() {
        return permisoRepository.findAll().stream()
                .map(PermisoDto::fromEntity)
                .toList();
    }

    @Override
    public PermisoDto obtenerPermisoPorId(Integer id) {
        try {
            log.info("Buscar permiso con id: {}", id);
            return permisoRepository.findById(id)
                    .map(PermisoDto::fromEntity)
                    .orElseThrow(() -> new ResourceNotFoundException("Permiso", id));
        } catch (ResourceNotFoundException e) {
            throw e;
        }
    }

    @Override
    public PermisoDto crearPermiso(CreatePermisoDto createPermisoDto) {
        if (isNombreExist(createPermisoDto.getNombre())) {
            log.error("El permiso con nombre {} ya existe", createPermisoDto.getNombre());
            throw new RuntimeException("El permiso ya existe");
        }
        
        Permiso permiso = CreatePermisoDto.toEntity(createPermisoDto);
        permiso = permisoRepository.save(permiso);
        
        return PermisoDto.fromEntity(permiso);
    }

    @Override
    public PermisoDto actualizarPermiso(Integer id, CreatePermisoDto createPermisoDto) {
        Permiso permisoRetrieved = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        
        if (isNombreExist(createPermisoDto.getNombre()) && 
            !permisoRetrieved.getNombre().equals(createPermisoDto.getNombre())) {
            throw new RuntimeException("El permiso con ese nombre ya existe");
        }
        
        permisoRetrieved.setNombre(createPermisoDto.getNombre());
        permisoRetrieved.setRecurso(createPermisoDto.getRecurso());
        permisoRetrieved.setAction(createPermisoDto.getAction());
        
        permisoRetrieved = permisoRepository.save(permisoRetrieved);
        
        return PermisoDto.fromEntity(permisoRetrieved);
    }

    @Override
    public void eliminarPermiso(Integer id) {
        permisoRepository.deleteById(id);
    }

    private boolean isNombreExist(String nombre) {
        return permisoRepository.existsByNombre(nombre);
    }
}
