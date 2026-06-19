package com.blumbit.CompraVentas2.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//AuthRequest - Peticion 
@Getter
@Setter
@AllArgsConstructor
@Builder
public class AuthRequest {
    private String username;
    private String password;

}
