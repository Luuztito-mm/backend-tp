package ar.edu.utn.frc.msrutastramos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/rutas")
public class InfoController {

    @GetMapping("/version")
    public Map<String, String> version() {
        return Map.of(
            "service", "ms-rutas-tramos",
            "version", "0.0.1"
        );
    }
}
