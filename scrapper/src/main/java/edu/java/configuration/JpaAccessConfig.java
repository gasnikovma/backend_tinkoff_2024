package edu.java.configuration;

import edu.java.repository.ChatRepository;
import edu.java.repository.LinkRepository;
import edu.java.repository.jpa.DefJpaChatRepository;
import edu.java.repository.jpa.DefJpaLinkRepository;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinkRepository jpaLinkRepository;

    @Autowired
    public JpaAccessConfig(JpaChatRepository jpaChatRepository, JpaLinkRepository jpaLinkRepository) {
        this.jpaChatRepository = jpaChatRepository;
        this.jpaLinkRepository = jpaLinkRepository;
    }

    @Bean
    public LinkRepository linkRepository() {
        return new DefJpaLinkRepository(jpaLinkRepository);
    }

    @Bean
    public ChatRepository chatRepository() {
        return new DefJpaChatRepository(jpaChatRepository);
    }

}
