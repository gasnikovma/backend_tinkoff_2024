package edu.java.scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableScheduling
public class LinkUpdateScheduler {
    private static final Logger log = LoggerFactory.getLogger(LinkUpdateScheduler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(fixedDelayString = "#{@interval.toMillis()}")
    public void update(){
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
