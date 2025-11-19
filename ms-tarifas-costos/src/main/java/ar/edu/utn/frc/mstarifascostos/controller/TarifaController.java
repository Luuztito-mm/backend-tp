package ar.edu.utn.frc.mstarifascostos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.mstarifascostos.model.Tarifa;
import ar.edu.utn.frc.mstarifascostos.service.TarifaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tarifas")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService tarifaService;

    // ================= CRUD =================

    @GetMapping
    public List<Tarifa> listar() {
        return tarifaService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> obtenerPorId(@PathVariable("id") Long id) {
        Tarifa tarifa = tarifaService.buscarPorId(id);
        return ResponseEntity.ok(tarifa);
    }

    @PostMapping
    public ResponseEntity<Tarifa> crear(@RequestBody Tarifa nuevaTarifa) {
        Tarifa guardada = tarifaService.guardar(nuevaTarifa);
        return ResponseEntity.ok(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarifa> actualizar(@PathVariable("id") Long id,
                                             @RequestBody Tarifa datosActualizados) {

        Tarifa actualizada = tarifaService.actualizar(id, datosActualizados);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        tarifaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ================= CÁLCULO DE COSTO CON DISTANCIA =================

    @GetMapping("/{id}/calcular")
    public ResponseEntity<Double> calcularCosto(
            @PathVariable("id") Long id,
            @RequestParam("distanciaKm") double distanciaKm,
            @RequestParam(name = "cantidadTramos", defaultValue = "1") int cantidadTramos) {

        double total = tarifaService.calcularCosto(id, distanciaKm, cantidadTramos);
        return ResponseEntity.ok(total);
    }

    // ============ CÁLCULO DE COSTO USANDO COORDENADAS (ms-rutas-tramos + OSRM) ============

    @GetMapping("/{id}/calcular-coordenadas")
    public ResponseEntity<Double> calcularCostoConCoordenadas(
            @PathVariable("id") Long id,
            @RequestParam("origenLat") double origenLat,
            @RequestParam("origenLon") double origenLon,
            @RequestParam("destinoLat") double destinoLat,
            @RequestParam("destinoLon") double destinoLon,
            @RequestParam(name = "cantidadTramos", defaultValue = "1") int cantidadTramos) {

        double total = tarifaService.calcularCostoConCoordenadas(
                id, origenLat, origenLon, destinoLat, destinoLon, cantidadTramos);

        return ResponseEntity.ok(total);
    }
}
