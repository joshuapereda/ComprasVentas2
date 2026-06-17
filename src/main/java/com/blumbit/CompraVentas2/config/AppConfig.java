package com.blumbit.CompraVentas2.config;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blumbit.CompraVentas2.entity.Usuario;
import com.blumbit.CompraVentas2.repository.UsuarioRepository;

@Configuration
public class AppConfig {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
           Usuario usuario= usuarioRepository.finByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
                List<GrantedAuthority> authorities =usuario.getRoles().stream()
                    .flatMap(rol -> {
                        Stream.Builder<GrantedAuthority> builder= Stream.builder();
                        //armamos roles y permisos
                        builder.add(new SimpleGrantedAuthority("ROL_"+rol.getNombre()));
                        rol.getPermisos().stream().map(permiso -> new SimpleGrantedAuthority(permiso.getNombre()))
                        .forEach(builder:: add);
                        return builder.build();
                    }).collect(java.util.stream.Collectors.toList());
                    return User.builder()
                        .username(usuario.getEmail())
                        .password(usuario.getPassword())
                        .authorities(authorities)
                        .build();
        };
    }
        //SIFRADO DE CONTRASEÑAS 
         @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
        //confirarar nuestro 
        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
        }
}
