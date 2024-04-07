package edu.java.scheduler;

import edu.java.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkUpdateScheduler {
    private final SchedulerService schedulerService;

    @Scheduled(fixedDelayString = "#{@interval. toMillis()}")
    public void update() {
        schedulerService.update();
    }
}
