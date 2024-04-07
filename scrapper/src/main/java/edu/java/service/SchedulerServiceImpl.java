package edu.java.service;

import edu.java.models.dto.Link;
import edu.java.repository.LinkRepository;
import edu.java.scheduler.GithubUpdateServiceImpl;
import edu.java.scheduler.StackOverFlowUpdateServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {
    private final LinkRepository linkRepository;
    private final GithubUpdateServiceImpl githubUpdateService;
    private final StackOverFlowUpdateServiceImpl stackOverFlowUpdateService;

    private final int amountOfLinks = 3;

    @Override
    public void update() {
        List<Link> links = linkRepository.getOldestLinks(amountOfLinks);
        for (int i = 0; i < links.size(); i++) {
            String[] uri = links.get(i).getUri().split("//");
            if (uri[1].startsWith("github.com")) {
                githubUpdateService.update(links.get(i));
            } else if (uri[1].startsWith("stackoverflow.com")) {
                stackOverFlowUpdateService.update(links.get(i));
            }
        }
    }
}
