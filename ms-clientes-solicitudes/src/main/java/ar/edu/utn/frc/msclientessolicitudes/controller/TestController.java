package ar.edu.utn.frc.msclientessolicitudes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/admin/test")
    public String adminTest() {
        return "🛠 Operador/Administrador OK";
    }

     @GetMapping("/cliente/test")
    public String clienteTest() {
        return " Cliente OK";
    }

    @GetMapping("/transportista/test")
    public String transportistaTest() {
        return "🚛 Transportista OK";
    }
}
