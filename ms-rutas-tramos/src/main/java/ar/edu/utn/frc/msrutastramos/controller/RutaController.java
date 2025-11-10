package ar.edu.utn.frc.msrutastramos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.msrutastramos.model.Ruta;
import ar.edu.utn.frc.msrutastramos.service.RutaService;

import java.util.List;

@RestController
@RequestMapping("/rutas")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    // POST /rutas -> crea ruta tentativa
    @PostMapping
    public ResponseEntity<Ruta> crearRuta(@RequestBody Ruta nuevaRuta) {
        Ruta rutaCreada = rutaService.crearRutaTentativa(nuevaRuta);
        return ResponseEntity.ok(rutaCreada);
    }

    // POST /rutas/{id}/asignar -> marca la ruta como definitiva
    @PostMapping("/{id}/asignar")
    public ResponseEntity<Ruta> asignarRuta(@PathVariable("id") Long id) {
        Ruta rutaAsignada = rutaService.asignarRuta(id);
        return ResponseEntity.ok(rutaAsignada);
    }

    // GET /rutas -> lista rutas
    @GetMapping
    public ResponseEntity<List<Ruta>> listarRutas() {
        List<Ruta> rutas = rutaService.listarRutas();
        return ResponseEntity.ok(rutas);
    }
}
