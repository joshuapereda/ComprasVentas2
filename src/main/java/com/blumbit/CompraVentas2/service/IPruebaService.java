package com.blumbit.CompraVentas2.service;

import java.util.List;

import com.blumbit.CompraVentas2.dto.CreatePruebaDto;
import com.blumbit.CompraVentas2.dto.PruebaDto;

public interface IPruebaService {
    List<PruebaDto> listarPruebas();
    PruebaDto obtenerPruebaPorId(Integer id);
    PruebaDto crearPrueba(CreatePruebaDto pruebaDto);
    PruebaDto actualizarPrueba(Integer id, CreatePruebaDto pruebaActualizada);
    void eliminarPrueba(Integer id);

}
