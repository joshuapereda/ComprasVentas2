package com.blumbit.CompraVentas2.dto;

import java.time.LocalDate;
import java.util.List;

import com.blumbit.CompraVentas2.entity.Persona;
import com.blumbit.CompraVentas2.entity.Usuario;
import com.blumbit.CompraVentas2.validation.UniqueName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CreateUsuarioDto {
    //-----------------------//
    //      TABLA USUARIO    //
    //-----------------------//
    //VALIDACIONES
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    //definir la longitud
    @Size(max=50, message = "El nombre de usuario no puede tener más de 50 caracteres")
    //@UniqueName(fieldName = "username")
    private String username;
    
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo no es válido")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía")
    //validacion con espresiones regulares para validar la contraseña
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial")
    private String password;
    //-----------------------//
    //     TABLA PERSONA     //
    //-----------------------//
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @NotBlank(message = "La fecha de nacimiento no puede estar vacía")
    @Pattern(
        regexp = "^\\d{2}/\\d{2}/\\d{4}$",
        message = "La fecha de nacimiento debe tener el formato DD/MM/YYYY")
    private String fechaNacimiento;

    @Size(max=20, message = "El género no puede tener más de 20 caracteres")
    private String genero;

    @Size(max=20, message = "El teléfono no puede tener más de 20 caracteres")
    private String telefono;

    @Size(max=100, message = "La dirección no puede tener más de 100 caracteres")
    private String direccion;

    @Size(max=50, message = "La nacionalidad no puede tener más de 50 caracteres")
    private String nacionalidad;

    //resivir rol
    @NotEmpty(message = "Debe asignar al menos un rol al usuario")
    private List<Integer>roles;
    

    //Todo add documentos
    public static Usuario toEntityUsuario(CreateUsuarioDto createUsuarioDto) {
        return Usuario.builder()
        .nombre(createUsuarioDto.getUsername())
        .email(createUsuarioDto.getCorreo())
        .password(createUsuarioDto.getPassword())
        .build();  
    }
    //pone a usuario por parametro para relacionar la persona con el usuario
     public static Persona toEntityPersona(CreateUsuarioDto createUsuarioDto, Usuario usuario) {
        return Persona.builder()
        .nombre(createUsuarioDto.getNombre())
        .apellido(createUsuarioDto.getApellido())
        //LocalDate -conversion 
        .fechaNacimiento(LocalDate.parse(createUsuarioDto.getFechaNacimiento(), java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        .genero(createUsuarioDto.getGenero())
        .telefono(createUsuarioDto.getTelefono())
        .direccion(createUsuarioDto.getDireccion())
        .nacionalidad(createUsuarioDto.getNacionalidad())
        //su relacion con usuario
        .usuario(usuario)
        .build();  
    }

}
