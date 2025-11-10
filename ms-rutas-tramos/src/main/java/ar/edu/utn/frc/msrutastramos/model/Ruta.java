package ar.edu.utn.frc.msrutastramos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad mínima de Ruta, alineada con el DER:
 *   - id
 *   - solicitud_id
 *   - asignada
 *   - cantidad_tramos
 *   - cantidad_depositos
 *
 * Se agrega la tabla "ruta".
 */

@Entity
@Table(name = "ruta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Id de la solicitud asociada (requerido por el modelo). */
    @Column(name = "solicitud_id", nullable = false)
    private Long solicitudId;

    /** Indica si la ruta fue asignada/confirmada definitivamente. */
    @Builder.Default
    @Column(nullable = false)
    private Boolean asignada = false;

    /** Cantidad de tramos que componen esta ruta (puede calcularse luego). */
    @Column(name = "cantidad_tramos")
    private Integer cantidadTramos;

    /** Cantidad de depósitos involucrados (puede calcularse luego). */
    @Column(name = "cantidad_depositos")
    private Integer cantidadDepositos;
}
