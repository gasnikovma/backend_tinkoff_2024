package edu.java.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class LinkUpdateScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(LinkUpdateScheduler.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelayString = "#{@interval.toMillis()}")
    public void update() {
        LOG.info("The time is now {}", DATE_FORMAT.format(new Date()));
    }
}
