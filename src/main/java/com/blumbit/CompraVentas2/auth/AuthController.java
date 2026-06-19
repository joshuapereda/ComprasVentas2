package com.blumbit.CompraVentas2.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blumbit.CompraVentas2.auth.dto.AuthRequest;
import com.blumbit.CompraVentas2.auth.dto.AuthResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/auth")
public class AuthController {
    //---------------------------------------------------------//
    //   Es el punto de entrada para las peticiones de         //
    //   autenticación. Recibe solicitudes de login y          //
    // refresh token y delega el procesamiento al AuthService. //
    //---------------------------------------------------------//
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader String authentication ) {
        return ResponseEntity.ok(authService.refreshToken(authentication));
    }
    
    

}
