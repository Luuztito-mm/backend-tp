package ar.edu.utn.frc.msdepositos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public/test")
    public String publicEndpoint() {
        return "🔓 Endpoint público - sin token";
    }

    @GetMapping("/admin/test")
    public String adminEndpoint() {
        return "🔒 Endpoint ADMIN (Operador) - acceso permitido";
    }

    
}

