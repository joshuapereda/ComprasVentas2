package com.blumbit.CompraVentas2.service;

import java.util.List;

import com.blumbit.CompraVentas2.dto.CreateUsuarioDto;
import com.blumbit.CompraVentas2.dto.UsuarioDto;

public interface IUsuarioService {
    List<UsuarioDto> listarUsuarios();
    UsuarioDto obtenerUsuarioPorId(Integer id);
    UsuarioDto crearUsuario(CreateUsuarioDto createUsuarioDto);
    UsuarioDto actualizarUsuario(Integer id, CreateUsuarioDto createUsuarioDto);
    void eliminarUsuario(Integer id);

}
