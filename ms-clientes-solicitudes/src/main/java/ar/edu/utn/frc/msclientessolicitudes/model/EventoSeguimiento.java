package ar.edu.utn.frc.msclientessolicitudes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "evento_seguimiento")
@Getter
@Setter
public class EventoSeguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solicitud_id", nullable = false)
    private Long solicitudId;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;

    @Column(name = "ubicacion_texto")
    private String ubicacionTexto;

    @Column(name = "deposito_id")
    private Long depositoId;
}
