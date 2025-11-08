package ar.edu.utn.frc.msrutastramos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import ar.edu.utn.frc.msrutastramos.model.Tramo;
import ar.edu.utn.frc.msrutastramos.repository.TramoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TramoService {

    private final TramoRepository tramoRepository;

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

    public double calcularDistancia(String origen, String destino) {
        List<Tramo> tramos = tramoRepository.findByOrigenAndDestino(origen, destino);
        return tramos.stream()
                     .mapToDouble(Tramo::getDistanciaKm)
                     .sum();
    }
}
