package ar.edu.utn.frc.msrutastramos.service;

import ar.edu.utn.frc.msrutastramos.exception.BadRequestException;
import ar.edu.utn.frc.msrutastramos.exception.ResourceNotFoundException;
import ar.edu.utn.frc.msrutastramos.model.Ruta;
import ar.edu.utn.frc.msrutastramos.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    /**
     * Crea una ruta tentativa. Fuerza asignada=false.
     * Valida campos mínimos para evitar basura en BD.
     */
    public Ruta crearRutaTentativa(Ruta nuevaRuta) {
        if (nuevaRuta == null) {
            throw new BadRequestException("El cuerpo de la solicitud no puede ser nulo.");
        }
        if (nuevaRuta.getSolicitudId() == null || nuevaRuta.getSolicitudId() <= 0) {
            throw new BadRequestException("El campo 'solicitudId' es obligatorio y debe ser > 0.");
        }
        if (nuevaRuta.getCantidadTramos() == null || nuevaRuta.getCantidadTramos() <= 0) {
            throw new BadRequestException("El campo 'cantidadTramos' es obligatorio y debe ser > 0.");
        }
        if (nuevaRuta.getCantidadDepositos() == null || nuevaRuta.getCantidadDepositos() < 0) {
            throw new BadRequestException("El campo 'cantidadDepositos' es obligatorio y no puede ser negativo.");
        }

        // siempre se crea como NO asignada
        nuevaRuta.setAsignada(false);
        return rutaRepository.save(nuevaRuta);
    }

    /**
     * Marca la ruta como definitiva (asignada = true).
     * Valida existencia y evita reasignar una ruta ya asignada.
     */
    public Ruta asignarRuta(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la ruta con id=" + id));

        if (Boolean.TRUE.equals(ruta.getAsignada())) {
            throw new BadRequestException("La ruta con id=" + id + " ya está asignada.");
        }

        ruta.setAsignada(true);
        return rutaRepository.save(ruta);
    }

    /**
     * Lista todas las rutas.
     */
    public List<Ruta> listarRutas() {
        return rutaRepository.findAll();
    }
}
