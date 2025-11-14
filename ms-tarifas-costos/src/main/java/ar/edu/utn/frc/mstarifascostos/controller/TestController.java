package ar.edu.utn.frc.mstarifascostos.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
    
    @GetMapping("/admin/test")
public String testAdmin() {
    return "🛠 Tarifas - acceso operador OK";
}


}






    
