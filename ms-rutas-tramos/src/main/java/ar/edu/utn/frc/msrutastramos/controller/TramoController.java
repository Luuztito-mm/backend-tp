package ar.edu.utn.frc.msrutastramos.controller;

import ar.edu.utn.frc.msrutastramos.model.Tramo;
import ar.edu.utn.frc.msrutastramos.service.TramoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tramos")
public class TramoController {

    private final TramoService tramoService;

    public TramoController(TramoService tramoService) {
        this.tramoService = tramoService;
    }

    @GetMapping
    public ResponseEntity<List<Tramo>> listar() {
        return ResponseEntity.ok(tramoService.listar());
    }

    @PostMapping
    public ResponseEntity<Tramo> crear(@RequestBody Tramo tramo) {
        return ResponseEntity.ok(tramoService.crear(tramo));
    }

    @PostMapping("/{id}/asignar-camion")
    public ResponseEntity<Tramo> asignarCamion(@PathVariable("id") Long id,
                                            @RequestParam("camionId") Long camionId) {
        return ResponseEntity.ok(tramoService.asignarCamion(id, camionId));
    }


    @PostMapping("/{id}/iniciar")
    public ResponseEntity<Tramo> iniciar(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tramoService.iniciar(id));
    }


    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Tramo> finalizar(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tramoService.finalizar(id));
    }

}
