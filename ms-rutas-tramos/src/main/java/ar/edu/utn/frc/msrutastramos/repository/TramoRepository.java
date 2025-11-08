package ar.edu.utn.frc.msrutastramos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.utn.frc.msrutastramos.model.Tramo;
import java.util.List;

public interface TramoRepository extends JpaRepository<Tramo, Long> {
    List<Tramo> findByOrigenAndDestino(String origen, String destino);
}
