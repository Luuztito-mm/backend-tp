package ar.edu.utn.frc.msrutastramos.repository;

import ar.edu.utn.frc.msrutastramos.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {

    // todas las rutas (alternativas) de una misma solicitud
    List<Ruta> findBySolicitudId(Long solicitudId);

    // rutas que ya están asignadas para una solicitud
    List<Ruta> findBySolicitudIdAndAsignadaTrue(Long solicitudId);
}
