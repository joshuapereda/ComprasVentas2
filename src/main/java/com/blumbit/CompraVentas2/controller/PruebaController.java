package com.blumbit.CompraVentas2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.CompraVentas2.dto.CreatePruebaDto;
import com.blumbit.CompraVentas2.dto.PruebaDto;
import com.blumbit.CompraVentas2.service.PruebaService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {
    @Autowired
    private PruebaService pruebaService;
 
     @GetMapping
     public List<PruebaDto> listarPruebas(){
        return pruebaService.listarPruebas();
     }
     @GetMapping("/{id}")
     public PruebaDto obtenerPruebaPorId(@PathVariable Integer id){
        return pruebaService.obtenerPruebaPorId(id);
     }
     @PostMapping
     public PruebaDto crearPrueba(@RequestBody CreatePruebaDto prueba){
        return pruebaService.crearPrueba(prueba);
     }
     @PutMapping("/{id}")
     public PruebaDto actualizarPrueba(@PathVariable Integer id, @RequestBody CreatePruebaDto pruebaActualizada){
       return pruebaService.actualizarPrueba(id, pruebaActualizada);
     }
     @DeleteMapping("/{id}")
     public void eliminarPrueba(@PathVariable Integer id){
        pruebaService.eliminarPrueba(id);
     }


}
