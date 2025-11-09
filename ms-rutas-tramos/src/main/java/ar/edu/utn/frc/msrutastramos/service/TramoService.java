package ar.edu.utn.frc.msrutastramos.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.msrutastramos.model.Tramo;
import ar.edu.utn.frc.msrutastramos.model.dto.RutaCalculadaDTO;
import ar.edu.utn.frc.msrutastramos.model.RutaResult;
import ar.edu.utn.frc.msrutastramos.repository.TramoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TramoService {

    private final TramoRepository tramoRepository;

    // ---------- CRUD ----------
    public List<Tramo> listar() {
        return tramoRepository.findAll();
    }

    public Optional<Tramo> buscarPorId(Long id) {
        return tramoRepository.findById(id);
    }

    public Tramo guardar(Tramo tramo) {
        return tramoRepository.save(tramo);
    }

    public void eliminar(Long id) {
        tramoRepository.deleteById(id);
    }

    // ---------- Cálculo de tramo directo ----------
    public double calcularDistancia(String origen, String destino) {
        List<Tramo> tramos = tramoRepository.findByOrigenAndDestino(origen, destino);

        if (tramos.isEmpty()) {
            throw new RuntimeException("No se encontró un tramo directo entre " + origen + " y " + destino);
        }

        return tramos.stream()
                .mapToDouble(Tramo::getDistanciaKm)
                .sum();
    }

    // ---------- Calcular mejor ruta (algoritmo de Dijkstra) ----------
    public RutaResult calcularMejorRuta(String origen, String destino) {
        // 1️⃣ Obtener todos los tramos
        List<Tramo> todos = tramoRepository.findAll();

        // 2️⃣ Construir grafo en memoria
        Map<String, List<Tramo>> adyacencia = todos.stream()
                .collect(Collectors.groupingBy(Tramo::getOrigen));

        // 3️⃣ Estructuras de soporte
        Map<String, Double> distancia = new HashMap<>();
        Map<String, Tramo> previo = new HashMap<>();
        PriorityQueue<String> cola = new PriorityQueue<>(Comparator.comparingDouble(distancia::get));

        // Inicialización
        Set<String> ciudades = new HashSet<>();
        todos.forEach(t -> {
            ciudades.add(t.getOrigen());
            ciudades.add(t.getDestino());
        });

        for (String c : ciudades) {
            distancia.put(c, Double.POSITIVE_INFINITY);
        }

        distancia.put(origen, 0.0);
        cola.add(origen);

        // 4️⃣ Algoritmo principal de Dijkstra
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            if (actual.equals(destino)) break;

            for (Tramo t : adyacencia.getOrDefault(actual, Collections.emptyList())) {
                String vecino = t.getDestino();
                double nuevaDist = distancia.get(actual) + t.getDistanciaKm();

                if (nuevaDist < distancia.getOrDefault(vecino, Double.POSITIVE_INFINITY)) {
                    distancia.put(vecino, nuevaDist);
                    previo.put(vecino, t);
                    cola.remove(vecino);
                    cola.add(vecino);
                }
            }
        }

        // 5️⃣ Si no hay camino posible
        if (!previo.containsKey(destino)) {
            return new RutaResult(origen, destino, 0, 0, Collections.emptyList());
        }

        // 6️⃣ Reconstruir la ruta desde destino hacia origen
        List<Tramo> camino = new ArrayList<>();
        String actual = destino;
        while (!actual.equals(origen)) {
            Tramo e = previo.get(actual);
            camino.add(e);
            actual = e.getOrigen();
        }
        Collections.reverse(camino);

        // 7️⃣ Calcular totales
        double totalDistancia = camino.stream().mapToDouble(Tramo::getDistanciaKm).sum();
        double totalDuracion = camino.stream().mapToDouble(Tramo::getDuracionHs).sum();

        // 8️⃣ Devolver DTO unificado
        return new RutaResult(origen, destino, totalDistancia, totalDuracion, camino);
    }
}
