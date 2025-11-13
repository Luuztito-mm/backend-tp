package ar.edu.utn.frc.msdepositos.controller;

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
        logger.info("Log INFO desde ms-depositos: {}", msg);
        return ResponseEntity.ok("log info ok (depositos) (" + msg + ")");
    }

    @GetMapping("/warn/{msg}")
    public ResponseEntity<String> logWarn(@PathVariable("msg") String msg) {
        logger.warn("Log WARN desde ms-depositos: {}", msg);
        return ResponseEntity.ok("log warn ok (depositos) (" + msg + ")");
    }

    @GetMapping("/error/{msg}")
    public ResponseEntity<String> logError(@PathVariable("msg") String msg) {
        logger.error("Log ERROR desde ms-depositos: {}", msg);
        return ResponseEntity.ok("log error ok (depositos) (" + msg + ")");
    }
}
