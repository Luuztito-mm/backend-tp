package ar.edu.utn.frc.msrutastramos.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public/test")
    public String publicEndpoint() {
        return "🔓 Público tarifas-costos";
    }

    

    @GetMapping("/admin/test")
    public String admin() {
        return "🟣 OPERADOR - tarifas-costos";
    }

    @GetMapping("/transportista/test")
    public String transportista() {
        return "🚛 TRANSPORTISTA - tarifas-costos";
    }
}