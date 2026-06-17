package com.blumbit.CompraVentas2.service;

import java.util.List;

import com.blumbit.CompraVentas2.dto.CreatePermisoDto;
import com.blumbit.CompraVentas2.dto.PermisoDto;

public interface IPermisoService {
    List<PermisoDto> listarPermisos();
    PermisoDto obtenerPermisoPorId(Integer id);
    PermisoDto crearPermiso(CreatePermisoDto createPermisoDto);
    PermisoDto actualizarPermiso(Integer id, CreatePermisoDto createPermisoDto);
    void eliminarPermiso(Integer id);
}
