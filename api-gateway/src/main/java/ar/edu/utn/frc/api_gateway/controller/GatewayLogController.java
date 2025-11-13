package ar.edu.utn.frc.api_gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway/logs")
public class GatewayLogController {

    private static final Logger logger = LoggerFactory.getLogger(GatewayLogController.class);

    // GET http://localhost:8080/gateway/logs/info/hola
    @GetMapping("/info/{msg}")
    public String info(@PathVariable("msg") String msg) {
        logger.info("Log INFO desde API Gateway: {}", msg);
        return "gateway log info ok (" + msg + ")";
    }

    // GET http://localhost:8080/gateway/logs/warn/hola
    @GetMapping("/warn/{msg}")
    public String warn(@PathVariable("msg") String msg) {
        logger.warn("Log WARN desde API Gateway: {}", msg);
        return "gateway log warn ok (" + msg + ")";
    }

    // GET http://localhost:8080/gateway/logs/error/hola
    @GetMapping("/error/{msg}")
    public String error(@PathVariable("msg") String msg) {
        logger.error("Log ERROR desde API Gateway: {}", msg);
        return "gateway log error ok (" + msg + ")";
    }
}
