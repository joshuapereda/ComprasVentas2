package com.blumbit.CompraVentas2.auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blumbit.CompraVentas2.entity.Usuario;
import com.blumbit.CompraVentas2.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
//copia
@Component

// Lombok genera automáticamente el constructor
// para todas las variables final
@RequiredArgsConstructor

/*
Filtro encargado de validar el JWT en cada petición.

Se ejecuta antes de que la solicitud llegue
a los controladores de la aplicación.
*/
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //--------------------------------------------------------------//
    //    Es un filtro que intercepta todas las peticiones HTTP.    //
    //   Su función es revisar si existe un JWT en el encabezado    //
    // Authorization, validarlo y registrar al usuario autenticado  //
    //      dentro del contexto de Spring Security.                 //
    //--------------------------------------------------------------//

    // Servicio encargado de generar y validar JWT
    private final JwtService jwtService;

    // Servicio utilizado por Spring Security para cargar usuarios
    private final UserDetailsService userDetailsService;

    // Repositorio para consultar usuarios en la base de datos
    private final UsuarioRepository usuarioRepository;

    /*
    Método ejecutado automáticamente por Spring Security
    en cada petición HTTP.
    */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        /*
        Si la petición corresponde al login,
        no es necesario validar JWT porque el usuario
        todavía no está autenticado.
        */
        if (request.getServletPath().contains("/login")) {

            // Continúa con la ejecución normal
            filterChain.doFilter(request, response);
            return;
        }

        /*
        Obtiene el encabezado Authorization.

        Ejemplo:

        Authorization: Bearer eyJhbGciOiJIUzI1Ni...
        */
        String authHeader =
                request.getHeader(HttpHeaders.AUTHORIZATION);

        /*
        Si no existe el encabezado Authorization
        o no comienza con Bearer,
        dejamos continuar la petición.
        */
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        /*
        Extrae únicamente el JWT eliminando
        el texto "Bearer ".
        */
        String token = authHeader.substring(7);

        /*
        Obtiene el email almacenado dentro del JWT.
        */
        String username =
                jwtService.extractUsername(token);

        /*
        Obtiene la autenticación actual almacenada
        por Spring Security.
        */
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        /*
        Validaciones:

        - Si el token no contiene usuario.
        - Si el usuario ya está autenticado.

        En ambos casos dejamos continuar.
        */
        if (username == null || authentication != null) {

            filterChain.doFilter(request, response);
            return;
        }

        /*
        Carga los datos del usuario utilizando
        UserDetailsService.
        */
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);

        /*
        Busca el usuario en la base de datos.
        */
        Usuario usuario =
                usuarioRepository.findByEmail(username)
                        .orElseThrow(() ->
                                new RuntimeException("Usuario no encontrado"));

        /*
        Verifica:

        - Que el token pertenezca al usuario.
        - Que el token no haya expirado.
        */
        if (jwtService.validateToken(token, usuario)) {

            /*
            Crea el objeto Authentication que Spring Security
            utilizará para considerar al usuario autenticado.
            */
            UsernamePasswordAuthenticationToken jwtAuthToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            /*
            Agrega información adicional de la petición.
            */
            jwtAuthToken.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            /*
            Guarda la autenticación dentro del contexto
            de Spring Security.

            Desde este momento Spring considera
            que el usuario está autenticado.
            */
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(jwtAuthToken);
        }

        /*
        Continúa con el siguiente filtro o controlador.
        */
        filterChain.doFilter(request, response);
    }
}