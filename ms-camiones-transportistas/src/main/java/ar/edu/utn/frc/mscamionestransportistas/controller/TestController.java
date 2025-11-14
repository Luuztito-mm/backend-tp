package ar.edu.utn.frc.mscamionestransportistas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/transportista/test")
    public String transportistaEndpoint() {
        return "🔓 Endpoint transportista - acceso permitido";
    }

    @GetMapping("/admin/test")
    public String adminEndpoint() {
        return "🔒 Endpoint ADMIN (Operador) - acceso permitido";
    }
}