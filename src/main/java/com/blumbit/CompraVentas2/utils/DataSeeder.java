package com.blumbit.CompraVentas2.utils;

import com.blumbit.CompraVentas2.config.AppConfig;
import com.blumbit.CompraVentas2.service.PermisoService;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.blumbit.CompraVentas2.entity.Permiso;
import com.blumbit.CompraVentas2.entity.Persona;
import com.blumbit.CompraVentas2.entity.Rol;
import com.blumbit.CompraVentas2.entity.Usuario;
import com.blumbit.CompraVentas2.repository.PermisoRepository;
import com.blumbit.CompraVentas2.repository.PersonaRepository;
import com.blumbit.CompraVentas2.repository.RolRepository;
import com.blumbit.CompraVentas2.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
//Inyecciones 
@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner  {

    private final UsuarioRepository usuarioRepository;
    private final PermisoRepository permisoRepository;
    private final RolRepository rolRepository;
    private final PersonaRepository persoRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //Inicializar  FAKER Y RANDOM 
        Faker faker= new Faker();
        Random random = new Random();

        List<String> acciones= List.of("crear","actualizar","listar","eliminar");
        List<String> recursos= List.of("usuarios","roles","permisos","productos","categorias","notas",
        "movimientos","sucursales","almacenes");

        List<Permiso> permisos=new ArrayList<>();

        if(permisoRepository.count() == 0){
            for(String recurso : recursos){
                for(String accion: acciones){
                    Permiso permiso = Permiso.builder()
                    .nombre(accion.toUpperCase()+"_"+recurso.toUpperCase())
                    .recurso(recurso)
                    .action(accion)
                    .build();
                    permisos.add(permisoRepository.save(permiso));
            }
        }
    }
        List<Rol> roles=new ArrayList<>();
         if(rolRepository.count() == 0){
           //------------------------------------//
           //          ROL ADMINISTRADOR         //
           //------------------------------------//  
           Rol adminRol = Rol.builder()
           .nombre("ADMIN")
           .descripcion("Rol administrador del sistema")
           .permisos(permisos)
           .build();
           roles.add(rolRepository.save(adminRol));
           
            //------------------------------------//
            //           ROL VENDEDOR             //
            //------------------------------------//     
            List<Permiso>permisosVendedor=permisos.stream()
                .filter(p->(List.of("productos","categorias","movimientos","notas").contains(p.getRecurso())
            //Indicar las acciones
            && List.of("listar","crear").contains(p.getAction()))).collect(Collectors.toList());

            //Guardar los permisos para vendedor
            Rol vendedorRol = Rol.builder()
            .nombre("VENDEDOR")
            .descripcion("Rol de vendedor para gestion de compras ventas")
            .permisos(permisosVendedor)
            .build();
            roles.add(rolRepository.save(vendedorRol)); 

            //------------------------------------//
            //             ROL RRHH               //
            //------------------------------------//  

            List<Permiso>permisosRrhh=permisos.stream()
            //Si no colocamnos el filtro | && List.of("listar","crear") le da por defecto todas las acciones
            .filter(p->(List.of("usuarios","roles","permisos").contains(p.getRecurso()) )).collect(Collectors.toList());

            //Guardar los permisos para  RRHH
            Rol rrhhRol = Rol.builder()
            .nombre("RRHH")
            .descripcion("Rol de usuario de RRHH")
            .permisos(permisosRrhh)
            .build();
            roles.add(rolRepository.save(rrhhRol)); 
    }
    //----------------------------------//
    //       CREAR DATOS DE PRUEBA      //
    //----------------------------------//
    if(usuarioRepository.count() ==0){
        for(int i=0; i <100; i++){
            //correo+i correo1, correo2 ...
            //usamos Faker y Random
            String correo= faker.internet().emailAddress();
            //CREAR USUARIO CON EL FAKER
            Usuario usuario= Usuario.builder()
                .email(correo)
                .nombre(faker.name().username())
                .password(passwordEncoder.encode("123456"))
                .roles(roles)
                .build();
            usuario= usuarioRepository.save(usuario);

            //CREAR PERSONAS CON EL FAKER
            Persona persona =Persona.builder()
                .apellido(faker.name().lastName())
                .nombre(faker.name().firstName())
                .direccion(faker.address().fullAddress())
                .genero(List.of("masculino","femenino","otros").get(random.nextInt(3)))
                .telefono(faker.phoneNumber().cellPhone())
                .usuario(usuario)
                .nacionalidad(faker.address().country())
                .fechaNacimiento(LocalDate.now().minusYears(18 + random.nextInt(50-18+1)).minusDays(random.nextInt(365)))
            .build();
            persoRepository.save(persona);
        }
    }
  }
}
