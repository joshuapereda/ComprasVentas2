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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.blumbit.CompraVentas2.entity.Usuario;
import com.blumbit.CompraVentas2.repository.UsuarioRepository;

@Configuration
public class AppConfig {
    @Autowired
    private UsuarioRepository usuarioRepository;

    /*CORS (Cross-Origin Resource Sharing) permite controlar qué aplicaciones
    pueden consumir nuestra API.
    Es muy útil cuando el Frontend y el Backend se encuentran en proyectos
    o dominios diferentes.
    Permite definir:
    - Qué orígenes pueden acceder.
    - Qué métodos HTTP están permitidos.
    - Qué encabezados pueden enviarse.*/
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    //Cualquier Acepto peticiones de cualquier origen
                    .allowedOrigins("*")
                    // Métodos HTTP permitidos para consumir la API.
                    .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                    // Permite enviar cualquier encabezado HTTP.
                    // Ejemplo: Authorization, Content-Type, Accept, etc.
                    .allowedHeaders("*");
            }
        };
    }

    /// UserDetailsService es el componente que Spring Security utiliza
    // para buscar usuarios durante el proceso de autenticación.
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            //Busca usuarios usando email
           Usuario usuario= usuarioRepository.findByEmail(username)
                //Si no encuentra el usuario lanza una excepcion
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
                // Convierte los roles y permisos del usuario en autoridades
                // que Spring Security utilizará para controlar accesos.
                //usuario.getRoles() optiene el rol de la BD 
                //El rol y permisos viene de la consulta ala BD
                List<GrantedAuthority> authorities =usuario.getRoles().stream()
                    .flatMap(rol -> {
                        Stream.Builder<GrantedAuthority> builder= Stream.builder();
                        // Agrega el rol del usuario como autoridad.
                        // Ejemplo: ROLE_ADMIN
                        builder.add(new SimpleGrantedAuthority("ROLE_"+rol.getNombre()));
                        // Agrega cada permiso asociado al rol.
                        // Ejemplo: VER_PRODUCTOS, CREAR_VENTAS, etc.
                        rol.getPermisos().stream().map(permiso -> new SimpleGrantedAuthority(permiso.getNombre()))
                        .forEach(builder:: add);
                        return builder.build();
                    }).collect(java.util.stream.Collectors.toList());
                    // Construye el objeto UserDetails que Spring Security utilizará
                    // para autenticar y autorizar al usuario.
                    return User.builder()
                        .username(usuario.getEmail())
                        .password(usuario.getPassword())
                        .authorities(authorities)
                        .build();
        };
    }
        // PasswordEncoder permite cifrar y validar contraseñas utilizando BCrypt.
         @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
        // AuthenticationProvider es el componente encargado de validar las credenciales enviadas durante el login.
        @Bean
        public AuthenticationProvider authenticationProvider() {
            // Utiliza UserDetailsService para buscar usuarios en la base de datos.
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
           // Utiliza BCrypt para comparar la contraseña ingresada
           // con la contraseña cifrada almacenada en la base de datos.
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        }

        /*
        AuthenticationManager es el componente principal encargado
        de coordinar el proceso de autenticación.

        Cuando un usuario intenta iniciar sesión:

        1. Recibe usuario y contraseña.
        2. Delega la validación al AuthenticationProvider.
        3. Si las credenciales son válidas, autentica al usuario.
        4. Si son incorrectas, lanza una excepción.
        */
        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration config) {

            // Obtiene el AuthenticationManager configurado por Spring Security
            return config.getAuthenticationManager();
        }
}
