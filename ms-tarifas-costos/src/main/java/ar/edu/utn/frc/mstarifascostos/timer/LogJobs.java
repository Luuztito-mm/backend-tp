package ar.edu.utn.frc.mstarifascostos.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@EnableScheduling
public class LogJobs {

    private static final Logger logger = LoggerFactory.getLogger(LogJobs.class);

    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
    public void tick() {

        Random rnd = new Random();
        LocalDateTime now = LocalDateTime.now();

        if (rnd.nextInt(10) < 7) {
            logger.info("Tick OK [Tarifas - {}]", now);
        } else {
            logger.error("Tick ERROR [Tarifas - {}]", now);
        }
    }
}
