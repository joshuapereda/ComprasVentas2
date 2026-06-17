package com.blumbit.CompraVentas2.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.blumbit.CompraVentas2.dto.CreatePruebaDto;
import com.blumbit.CompraVentas2.dto.PruebaDto;
import com.blumbit.CompraVentas2.entity.Prueba;
import com.blumbit.CompraVentas2.repository.PruebaRepository;

@Service
public class PruebaService implements IPruebaService {

    @Autowired
    private PruebaRepository pruebaRepository;

    public List<PruebaDto> listarPruebas(){
        return pruebaRepository.findAll().stream()
            .map(PruebaDto::fromEntity)
            .collect(java.util.stream.Collectors.toList());
    }
    public PruebaDto obtenerPruebaPorId(Integer id){
        return PruebaDto.fromEntity(pruebaRepository.findById(id).get());
    }
    public PruebaDto crearPrueba(CreatePruebaDto prueba){
        Prueba nuevPrueba=CreatePruebaDto.toEntity(prueba);
        return PruebaDto.fromEntity(pruebaRepository.save(nuevPrueba));
    }  
    public PruebaDto  actualizarPrueba(Integer id, CreatePruebaDto pruebaActualizada){
        Prueba pruebaExistente=pruebaRepository.findById(id).get();
        pruebaExistente.setNombre(pruebaActualizada.getNombre());
        pruebaExistente.setApellido(pruebaActualizada.getApellido());
        pruebaExistente.setDescripcion(pruebaActualizada.getDescripcion());
        return PruebaDto.fromEntity(pruebaRepository.save(pruebaExistente));
    }
    public void eliminarPrueba(Integer id){
        pruebaRepository.deleteById(id);
    }
    
   
   

}
