package ar.edu.utn.frc.msrutastramos.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RutaResult {
    private String origen;
    private String destino;
    private double distanciaTotalKm;
    private double duracionTotalHs;
    private List<Tramo> tramos;
}
