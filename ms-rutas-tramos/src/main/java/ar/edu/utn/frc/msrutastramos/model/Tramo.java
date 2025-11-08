package ar.edu.utn.frc.msrutastramos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tramo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origen;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private Double distanciaKm;

    @Column(nullable = false)
    private Double duracionHs;
}
