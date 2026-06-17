package com.blumbit.CompraVentas2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HealhCheckController {
    @GetMapping("/health")
    public String healthCheck() {
        return "Hola Mundo";
    }

}
