package com.blumbit.CompraVentas2.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blumbit.CompraVentas2.auth.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SegurityConfig {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    //DEFINIR SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        //LOGICA
        try {
            http
                /*Desactivamos CSRF porque nuestra aplicación utiliza autenticación
                basada en JWT y trabaja en modo Stateless. No dependemos de sesiones 
                del servidor ni de cookies de sesión que el navegador envía automáticamente. 
                En cada petición el cliente debe enviar explícitamente el token JWT en el encabezado,
                por lo que la protección CSRF generalmente no es necesaria. */
                .csrf(csrf -> csrf.disable())
                //DAR SEGURIDAD A LAS PETICIONES 
                .authorizeHttpRequests(auth -> auth
                    
                    // permitAll() -> Permite acceder a esta ruta sin estar autenticado
                    // Es decir, no requiere enviar un JWT válido
                    .requestMatchers("/login").permitAll()
                    // hasAnyAuthority() -> Solo permite acceder si el usuario posee
                    // alguno de los permisos o roles especificados
                    .requestMatchers("/productos").hasAnyAuthority("LISTAR_PRODUCTOS","ROLE_ENCARGADO_ALMACEN")
                    .requestMatchers("/ventas").hasAnyAuthority("ROL_ADMIN")
                    // Cualquier otra petición requiere autenticación.
                    // Para acceder debe enviarse un JWT válido.
                    .anyRequest().authenticated()
                )
                // Habilita la configuración CORS definida en AppConfig.
                // Permite controlar qué aplicaciones pueden consumir la API.   
                .cors(Customizer.withDefaults())
                // Stateless significa que Spring Security NO almacenará sesiones.
                // Cada petición deberá autenticarse enviando un JWT.
                // El servidor no recuerda usuarios entre peticiones.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Registramos el AuthenticationProvider que será el encargado
                // de validar usuario y contraseña durante el login.
                .authenticationProvider(authenticationProvider)
                // Agregamos nuestro filtro JWT antes del filtro de autenticación
                // de Spring Security para validar el token en cada petición.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        } catch (Exception e) {
          throw new RuntimeException("Error configurando Filter chain");
        }
    }
}
