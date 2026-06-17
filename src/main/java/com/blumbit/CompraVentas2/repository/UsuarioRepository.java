    package com.blumbit.CompraVentas2.repository;

    import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blumbit.CompraVentas2.entity.Usuario;

    public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
        //QUEARY METHODS
       

        //METODO QUE BUSQUE POR CORREO
        Optional<Usuario>finByEmail(String email);
        //DEFINIMOS DE LA CLASE UniqueNameChecker 
        Usuario findByNombre(String nombre);
        //va buscar un registro y va devolver un booleano(true o false )
        boolean existsByNombre(String nombre);
        @Query(value = "SELECT EXISTS (SELECT 1 FROM usuarios WHERE email=?1)",nativeQuery = true)
        boolean existsByEmail(String email);
    }
