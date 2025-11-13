package ar.edu.utn.frc.mscamionestransportistas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
public class LogDemoController {

    private static final Logger logger = LoggerFactory.getLogger(LogDemoController.class);

    @GetMapping("/info/{msg}")
    public ResponseEntity<String> logInfo(@PathVariable("msg") String msg) {
        logger.info("Log INFO desde ms-camiones-transportistas: {}", msg);
        return ResponseEntity.ok("log info ok (camiones-transportistas) (" + msg + ")");
    }

    @GetMapping("/warn/{msg}")
    public ResponseEntity<String> logWarn(@PathVariable("msg") String msg) {
        logger.warn("Log WARN desde ms-camiones-transportistas: {}", msg);
        return ResponseEntity.ok("log warn ok (camiones-transportistas) (" + msg + ")");
    }

    @GetMapping("/error/{msg}")
    public ResponseEntity<String> logError(@PathVariable("msg") String msg) {
        logger.error("Log ERROR desde ms-camiones-transportistas: {}", msg);
        return ResponseEntity.ok("log error ok (camiones-transportistas) (" + msg + ")");
    }
}
