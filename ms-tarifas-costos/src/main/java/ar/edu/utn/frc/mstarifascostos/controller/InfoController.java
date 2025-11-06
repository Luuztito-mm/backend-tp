package ar.edu.utn.frc.mstarifascostos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/tarifas")
public class InfoController {

    @GetMapping("/version")
    public Map<String, String> version() {
        return Map.of(
            "service", "ms-tarifas-costos",
            "version", "0.0.1"
        );
    }
}
