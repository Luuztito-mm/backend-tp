package ar.edu.utn.frc.mstarifascostos.service;

import ar.edu.utn.frc.mstarifascostos.exception.BadRequestException;
import ar.edu.utn.frc.mstarifascostos.exception.ResourceNotFoundException;
import ar.edu.utn.frc.mstarifascostos.model.Tarifa;
import ar.edu.utn.frc.mstarifascostos.model.dto.DistanciaDTO;
import ar.edu.utn.frc.mstarifascostos.repository.TarifaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarifaService {

    private final TarifaRepository tarifaRepository;
    private final RutasTramosClient rutasTramosClient;   // depende de ms-rutas-tramos

    // ===================== CRUD =====================

    public List<Tarifa> listar() {
        return tarifaRepository.findAll();
    }

    public Tarifa buscarPorId(Long id) {
        return tarifaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No existe tarifa con id=" + id));
    }

    public Tarifa guardar(Tarifa tarifa) {
        validarTarifa(tarifa);
        return tarifaRepository.save(tarifa);
    }

    /** Actualiza una tarifa existente */
    public Tarifa actualizar(Long id, Tarifa datosActualizados) {
        Tarifa existente = buscarPorId(id); // lanza ResourceNotFound si no existe

        existente.setNombre(datosActualizados.getNombre());
        existente.setRangoPesoMin(datosActualizados.getRangoPesoMin());
        existente.setRangoPesoMax(datosActualizados.getRangoPesoMax());
        existente.setRangoVolumenMin(datosActualizados.getRangoVolumenMin());
        existente.setRangoVolumenMax(datosActualizados.getRangoVolumenMax());
        existente.setCostoKmBase(datosActualizados.getCostoKmBase());
        existente.setCargoGestionPorTramo(datosActualizados.getCargoGestionPorTramo());

        validarTarifa(existente);
        return tarifaRepository.save(existente);
    }

    public void eliminar(Long id) {
        Tarifa existente = buscarPorId(id); // lanza excepción si no está
        tarifaRepository.delete(existente);
    }

    // ===================== REGLAS DE NEGOCIO =====================

    public double calcularCosto(Long tarifaId,
                                double distanciaKm,
                                int cantidadTramos) {

        if (distanciaKm < 0) {
            throw new BadRequestException("La distancia en km no puede ser negativa.");
        }
        if (cantidadTramos <= 0) {
            throw new BadRequestException("La cantidad de tramos debe ser mayor a cero.");
        }

        Tarifa tarifa = buscarPorId(tarifaId);

        BigDecimal distancia = BigDecimal
                .valueOf(distanciaKm)
                .setScale(3, RoundingMode.HALF_UP);

        return calcularCosto(tarifa, distancia, cantidadTramos);
    }

    /** Variante que recibe coordenadas y delega la distancia a ms-rutas-tramos */
    public double calcularCostoConCoordenadas(Long tarifaId,
                                              double origenLat,
                                              double origenLon,
                                              double destinoLat,
                                              double destinoLon,
                                              int cantidadTramos) {

        if (cantidadTramos <= 0) {
            throw new BadRequestException("La cantidad de tramos debe ser mayor a cero.");
        }

        // 1) Pedimos la distancia a ms-rutas-tramos
        DistanciaDTO dto = rutasTramosClient.obtenerDistancia(
                origenLat, origenLon,
                destinoLat, destinoLon
        );

        // 2) metros → km
        BigDecimal distanciaKm = BigDecimal
                .valueOf(dto.getDistanciaMetros())
                .divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP);

        // 3) Calculamos el costo reutilizando la misma lógica
        Tarifa tarifa = buscarPorId(tarifaId);
        return calcularCosto(tarifa, distanciaKm, cantidadTramos);
    }

    // ===================== PRIVADOS =====================

    private double calcularCosto(Tarifa tarifa,
                                 BigDecimal distanciaKm,
                                 int cantidadTramos) {

        if (tarifa.getCostoKmBase() == null || tarifa.getCargoGestionPorTramo() == null) {
            throw new BadRequestException("La tarifa no tiene definidos costoKmBase o cargoGestionPorTramo.");
        }

        BigDecimal costoKmBase = tarifa.getCostoKmBase();
        BigDecimal cargoGestionPorTramo = tarifa.getCargoGestionPorTramo();

        BigDecimal costoDistancia = costoKmBase.multiply(distanciaKm);
        BigDecimal costoTramos = cargoGestionPorTramo.multiply(
                BigDecimal.valueOf(cantidadTramos)
        );

        BigDecimal total = costoDistancia.add(costoTramos);
        return total.doubleValue();
    }

    private void validarTarifa(Tarifa tarifa) {
        if (tarifa.getNombre() == null || tarifa.getNombre().isBlank()) {
            throw new BadRequestException("El nombre de la tarifa es obligatorio.");
        }
        if (tarifa.getRangoPesoMin() == null || tarifa.getRangoPesoMax() == null) {
            throw new BadRequestException("Los rangos de peso son obligatorios.");
        }
        if (tarifa.getRangoVolumenMin() == null || tarifa.getRangoVolumenMax() == null) {
            throw new BadRequestException("Los rangos de volumen son obligatorios.");
        }
        if (tarifa.getCostoKmBase() == null || tarifa.getCostoKmBase().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("El costoKmBase debe ser >= 0.");
        }
        if (tarifa.getCargoGestionPorTramo() == null || tarifa.getCargoGestionPorTramo().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("El cargoGestionPorTramo debe ser >= 0.");
        }
    }
}
