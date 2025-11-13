package ar.edu.utn.frc.msdepositos.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class LogJobs {
    private static final Logger log = LoggerFactory.getLogger(LogJobs.class);

    // cada 10s (delay inicial 5s)
    @Scheduled(initialDelay = 5_000, fixedDelay = 10_000)
    public void heartbeat() {
        log.info("Depositos heartbeat [{}]", LocalDateTime.now());
    }
}
