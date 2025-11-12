package ar.edu.utn.frc.msrutastramos.service;

import ar.edu.utn.frc.msrutastramos.exception.BadRequestException;
import ar.edu.utn.frc.msrutastramos.model.Tramo;
import ar.edu.utn.frc.msrutastramos.repository.TramoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TramoService {

    private final TramoRepository tramoRepository;

    public TramoService(TramoRepository tramoRepository) {
        this.tramoRepository = tramoRepository;
    }

    public List<Tramo> listar() {
        return tramoRepository.findAll();
    }

    public Tramo crear(Tramo tramo) {
        tramo.setEstado("PENDIENTE");
        return tramoRepository.save(tramo);
    }

    public Tramo asignarCamion(Long id, Long camionId) {
        Tramo tramo = tramoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No existe un tramo con id=" + id));

        if (tramo.getCamionId() != null) {
            throw new BadRequestException("El tramo con id=" + id + " ya tiene un camión asignado.");
        }

        tramo.setCamionId(camionId);
        return tramoRepository.save(tramo);
    }

    public Tramo iniciar(Long id) {
        Tramo tramo = tramoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No existe un tramo con id=" + id));

        if (!tramo.puedeIniciarse()) {
            throw new BadRequestException("El tramo con id=" + id + " no puede iniciarse porque su estado actual es: " + tramo.getEstado());
        }

        tramo.setEstado("EN_PROCESO");
        return tramoRepository.save(tramo);
    }

    public Tramo finalizar(Long id) {
        Tramo tramo = tramoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No existe un tramo con id=" + id));

        if (!tramo.puedeFinalizarse()) {
            throw new BadRequestException("El tramo con id=" + id + " no puede finalizarse porque su estado actual es: " + tramo.getEstado());
        }

        tramo.setEstado("FINALIZADO");
        return tramoRepository.save(tramo);
    }
}
