package ar.edu.utn.frc.msclientessolicitudes.controller;

import ar.edu.utn.frc.msclientessolicitudes.model.Cliente;
import ar.edu.utn.frc.msclientessolicitudes.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // GET /clientes
    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    // POST /clientes
    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente) {
        Cliente guardado = clienteRepository.save(cliente);
        return ResponseEntity
                .created(URI.create("/clientes/" + guardado.getId()))
                .body(guardado);
    }
}
