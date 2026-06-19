package com.blumbit.CompraVentas2.auth;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.blumbit.CompraVentas2.entity.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
//copia
@Service
public class JwtService {
    //-------------------------------------------------------------//
    // Es la clase encargada de generar, leer y validar los JWT.   //
    // También verifica si un token ha expirado y permite extraer  //
    // Información almacenada dentro del token, como el correo     //
    //               electrónico del usuario.                      //
    //-------------------------------------------------------------//

    /*JwtService es la clase encargada de:
    - Generar Access Tokens.
    - Generar Refresh Tokens.
    - Extraer información de un Token.
    - Validar Tokens.
    - Verificar expiración.
    Es la clase principal que trabaja con JWT.*/

    // Obtiene la clave secreta definida en application.yml.
    // Esta clave se utiliza para firmar y validar los JWT.
    @Value("${application.jwt.secret-key}")
    private String secretKey;

    @Value("${application.jwt.access-token-expiration}")
    private long jwtExpiration;

    @Value("${application.jwt.refresh-token-expiration}")
    private long refreshExpiration;

    //OPTENER EL USERNAME RELACIONADO CON EL JWT TOKEN 
    public String extractUsername(String token){
        // Inicia el proceso de lectura del JWT.
        return  Jwts.parser()
        // Verifica que el token fue firmado con nuestra clave secreta.
        .verifyWith(getSignkey())
        .build()
        // Abre el contenido interno del JWT.
        .parseSignedClaims(token)
        // Obtiene los datos almacenados dentro del token.
        .getPayload()
        // Obtiene el Subject del JWT.
        // En nuestro caso el Subject es el email del usuario.
        .getSubject();
    }
    /* Método auxiliar encargado de construir un JWT.
    Es utilizado tanto para generar Access Tokens
    como para generar Refresh Tokens.*/

    public String buildToken(Usuario usuario, Long expiration){
        return Jwts.builder()
        // Almacena el email del usuario dentro del JWT.
        // Luego podremos recuperarlo usando extractUsername().
        .subject(usuario.getEmail())
        // Agrega información adicional al JWT.
        // En este caso almacena el nombre del usuario.
        .claims(Map.of("uid", "_"+usuario.getNombre()))
        // Fecha y hora en la que se creó el token.
        .issuedAt(new Date(System.currentTimeMillis()))
        // Fecha y hora de vencimiento del token.
        .expiration(new Date(System.currentTimeMillis()+expiration))
        // Fecha y hora de vencimiento del token.
        .signWith(getSignkey())
        // Convierte toda la información en un String JWT.
        .compact();
    }
    // Genera un Access Token utilizando el tiempo
    // de expiración configurado para acceso.
    public String generateAccessToken(Usuario usuario){
        return buildToken(usuario,jwtExpiration);
    }
    // Genera un Refresh Token utilizando un tiempo
    // de expiración más largo.
    public String generateRefreshToken(Usuario usuario){
        return buildToken(usuario,refreshExpiration);
    }
    // Verifica si el JWT ya venció.
    public boolean isTokenExpired(String token){
        // Obtiene la fecha de expiración almacenada dentro del JWT. 
        Date expiration = Jwts.parser()
            .verifyWith(getSignkey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
        // Si la fecha de expiración es anterior a la fecha actual,
        // significa que el token ya venció.
        return expiration.before(new Date());
    }
    /*Valida que:
    1. El token pertenezca al usuario.
    2. El token no haya expirado.*/
    public boolean validateToken(String token,Usuario usuario){
        // Obtiene el email almacenado dentro del JWT.
        String username = extractUsername(token);
        // Retorna true solamente si:
        // - El email coincide.
        // - El token sigue vigente.
        return username.equals(usuario.getEmail()) && !isTokenExpired(token);
    }


   /*Convierte la clave secreta almacenada en application.yml
   en una SecretKey que la librería JWT pueda utilizar.*/
    private SecretKey getSignkey(){
        byte []keyBites = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBites);
    }

}
