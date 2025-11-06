package ar.edu.utn.frc.msdepositos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/depositos")
public class InfoController {

    @GetMapping("/version")
    public Map<String, String> version() {
        return Map.of(
            "service", "ms-depositos",
            "version", "0.0.1"
        );
    }
}
