package com.blumbit.CompraVentas2.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blumbit.CompraVentas2.dto.CreateUsuarioDto;
import com.blumbit.CompraVentas2.dto.UsuarioDto;
import com.blumbit.CompraVentas2.entity.Persona;
import com.blumbit.CompraVentas2.entity.Rol;
import com.blumbit.CompraVentas2.entity.Usuario;
import com.blumbit.CompraVentas2.exception.ConflictException;
import com.blumbit.CompraVentas2.exception.ResourceNotFoundException;
import com.blumbit.CompraVentas2.repository.PersonaRepository;
import com.blumbit.CompraVentas2.repository.RolRepository;
import com.blumbit.CompraVentas2.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
//IMPLEMENTAR LOOGS CON LOMBOOK
@Slf4j
public class UsuarioService implements IUsuarioService  {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private RolRepository rolRepository;
    @Override
    public List<UsuarioDto> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(usuario ->{
           // Persona persona=personaRepository.findByUsuario(usuario);
           //se traia por separado por que no habia una relacion
            return UsuarioDto.fromEntityUsuario(usuario, usuario.getPersona());
        }).toList();
    }

    @Override
    public UsuarioDto obtenerUsuarioPorId(Integer id) {
        try {
            log.info("Buscar usuario con id: {}",id);
            return usuarioRepository.findById(id).map(usuario -> {
            //Persona persona = personaRepository.findByUsuario(usuario);
            return UsuarioDto.fromEntityUsuario(usuario, usuario.getPersona());
            }).orElseThrow(() -> new ResourceNotFoundException("Uusuario ",id ));
        } catch (ResourceNotFoundException e) {
            throw e;
        }
      
    }
    @Transactional
    @Override
    public UsuarioDto crearUsuario(CreateUsuarioDto createUsuarioDto) {
        if(isEmailExist(createUsuarioDto.getCorreo())){
             log.error("El corre electronico ya esta en uso ");
             throw new RuntimeException("El correo ya esta en uso ");
        }
        //AÑADIR ROLES
        List<Rol>roles=rolRepository.findAllById(createUsuarioDto.getRoles());

        Usuario usuario=CreateUsuarioDto.toEntityUsuario(createUsuarioDto);
                usuario.setRoles(roles);
        usuario=usuarioRepository.save(usuario);
        Persona persona = CreateUsuarioDto.toEntityPersona(createUsuarioDto, usuario);
        persona=personaRepository.save(persona);  
        return UsuarioDto.fromEntityUsuario(usuario, persona);
      }

    @Override
    public UsuarioDto actualizarUsuario(Integer id, CreateUsuarioDto createUsuarioDto) {
        if(isEmailExist(createUsuarioDto.getCorreo())){
            throw new RuntimeException("El correo electronico ya está en uso");
        }
        Usuario usuarioRetrieved = usuarioRepository.findById(id).orElseThrow(() 
        -> new RuntimeException("Usuario no encontrado"));
        
        usuarioRetrieved.setRoles(rolRepository.findAllById(createUsuarioDto.getRoles()));
        usuarioRetrieved.setEmail(createUsuarioDto.getCorreo());
        usuarioRetrieved.setPassword(createUsuarioDto.getPassword());
        usuarioRetrieved.setNombre(createUsuarioDto.getNombre());
        Persona personaRetrieved = personaRepository.findByUsuario(usuarioRetrieved);
        personaRetrieved.setNombre(createUsuarioDto.getNombre());
        personaRetrieved.setApellido(createUsuarioDto.getApellido());
        personaRetrieved.setTelefono(createUsuarioDto.getTelefono());
        personaRetrieved.setDireccion(createUsuarioDto.getDireccion());
        personaRetrieved.setNacionalidad(createUsuarioDto.getNacionalidad());
        personaRetrieved.setFechaNacimiento(LocalDate.parse(createUsuarioDto.getFechaNacimiento()));
        return UsuarioDto.fromEntityUsuario(usuarioRepository.save(usuarioRetrieved), personaRepository.save(personaRetrieved));
    }

    @Override
    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    //VALIDACIONES CON 
    boolean isEmailExist(String email){
        return usuarioRepository.existsByEmail(email);
    }
}
