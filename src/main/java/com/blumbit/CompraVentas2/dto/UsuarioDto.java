package com.blumbit.CompraVentas2.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.blumbit.CompraVentas2.entity.Persona;
import com.blumbit.CompraVentas2.entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {
    //CADA VA LO QUE VAMO A DEVOLVER -UN LISTADO 
     private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String direccion;
    private String nacionalidad;
     private String fechaNacimiento;

    private List<Integer> roles;
    
    public static UsuarioDto fromEntityUsuario(Usuario usuario,Persona persona) {
        return UsuarioDto.builder()
        .id(usuario.getId())
        .nombre(persona.getNombre())
        .apellido(persona.getApellido())
        .correo(usuario.getEmail())
        .telefono(persona.getTelefono())
        .direccion(persona.getDireccion())
        .nacionalidad(persona.getNacionalidad())
        //format - convierte a un formato cadena 
        .fechaNacimiento(persona.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        .roles(usuario.getRoles().stream().map(rol->rol.getId()).toList())
        .build();
    }
}
