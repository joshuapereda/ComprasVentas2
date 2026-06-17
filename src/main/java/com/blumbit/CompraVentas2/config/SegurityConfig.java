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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SegurityConfig {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    //DEFINIR SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        //LOGICA
        try {
            http
                .csrf(csrf -> csrf.disable())
                //DAR SEGURIDAD A LAS CREDENCIALES 
                .authorizeHttpRequests(auth -> auth
                    //MACH - VER SI HAY UNA PETICION DE ALGUNA RUTA EN PARTICULAR 
                    .requestMatchers("/login").permitAll()
                    //VALIDAR DISTINTAS RUTAS DEL SERVICIO
                    // VA PEDIR PERMISOS Y ROLES 
                    .requestMatchers("/productos").hasAnyAuthority("VER_PRODUCTOS","ROLE_ENCARGADO_ALMACEN")
                    .requestMatchers("/ventas").hasAnyAuthority("VER_VENTAS","ROLES_VENDEDOR")
                    //PARA CUALQUIE PETICION VA ESTA AUTENTICADO OSES VA PEDIR TOKEN VALIDO
                    .anyRequest().authenticated()
                )
                //SEGURIDAD DE LOS CORS
                //Customizer - la estencin de segurity config 
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                //añadir un filtro
                .addFilterBefore(null, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        } catch (Exception e) {
          throw new RuntimeException("Error configurando Filter chain");
        }
    }
}
