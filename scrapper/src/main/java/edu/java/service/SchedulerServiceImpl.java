package edu.java.service;

import edu.java.models.dto.Link;
import edu.java.repository.LinkRepository;
import edu.java.scheduler.GithubUpdateServiceImpl;
import edu.java.scheduler.StackOverFlowUpdateServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {
    private final LinkRepository linkRepository;
    private final GithubUpdateServiceImpl githubUpdateService;
    private final StackOverFlowUpdateServiceImpl stackOverFlowUpdateService;

    @Override
    public void update() {
        List<Link> links = linkRepository.getOldestLinks(10);
        for (int i = 0; i < links.size(); i++) {
            String[] uri = links.get(i).getUri().split("//");
            if (uri[1].startsWith("github.com") && githubUpdateService.isCorrectUri(links.get(i).getUri())) {
                log.info(String.valueOf(links.get(i)));
                githubUpdateService.update(links.get(i));
            } else if (uri[1].startsWith("stackoverflow.com")
                && stackOverFlowUpdateService.isCorrectUri(links.get(i).getUri())) {
               // log.info(String.valueOf(links.get(i)));
                stackOverFlowUpdateService.update(links.get(i));
            }
        }
    }
}
