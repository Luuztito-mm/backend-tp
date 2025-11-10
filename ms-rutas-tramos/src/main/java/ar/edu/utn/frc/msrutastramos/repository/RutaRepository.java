package ar.edu.utn.frc.msrutastramos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ar.edu.utn.frc.msrutastramos.model.Ruta;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {

    // Aquí se pueden agregar métodos de consulta personalizados si es necesario
}
