package edu.java.scheduler;
<<<<<<< HEAD

import java.text.SimpleDateFormat;
import java.util.Date;
=======
>>>>>>> 8a3421a (hw2_first)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
<<<<<<< HEAD

@EnableScheduling
public class LinkUpdateScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(LinkUpdateScheduler.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelayString = "#{@interval.toMillis()}")
    public void update() {
        LOG.info("The time is now {}", DATE_FORMAT.format(new Date()));
=======
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableScheduling
public class LinkUpdateScheduler {
    private static final Logger log = LoggerFactory.getLogger(LinkUpdateScheduler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(fixedDelayString = "#{@interval.toMillis()}")
    public void update(){
        log.info("The time is now {}", dateFormat.format(new Date()));
>>>>>>> 8a3421a (hw2_first)
    }
}
