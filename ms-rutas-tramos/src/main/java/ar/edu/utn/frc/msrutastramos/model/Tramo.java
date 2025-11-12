package ar.edu.utn.frc.msrutastramos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tramo")
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "camion_id")
    private Long camionId;

    @Column(name = "ruta_id")
    private Long rutaId;

    @Column(name = "estado")
    private String estado; // "PENDIENTE", "EN_PROCESO", "FINALIZADO"

    @Column(name = "origen")
    private String origen; // Nueva columna obligatoria

    @Column(name = "destino")
    private String destino;

    @Column(name = "distancia_km")
    private Double distanciaKm;

    @Column(name = "duracion_hs")
    private Double duracionHs;

    public boolean puedeIniciarse() {
        return "PENDIENTE".equalsIgnoreCase(estado);
    }

    public boolean puedeFinalizarse() {
        return "EN_PROCESO".equalsIgnoreCase(estado);
    }
}
