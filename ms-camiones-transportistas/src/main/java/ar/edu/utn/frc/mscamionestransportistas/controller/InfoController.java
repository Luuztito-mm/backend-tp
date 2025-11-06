package ar.edu.utn.frc.mscamionestransportistas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/camiones")
public class InfoController {

    @GetMapping("/version")
    public Map<String, String> version() {
        return Map.of(
            "service", "ms-camiones-transportistas",
            "version", "0.0.1"
        );
    }
}

