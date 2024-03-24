package edu.java.repository.jpa;

import edu.java.exceptions.ChatAlreadyExistsException;
import edu.java.models.dto.Chat;
import edu.java.repository.ChatRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public class DefJpaChatRepository implements ChatRepository {

    private final JpaChatRepository jpaChatRepository;

    @Override @Transactional public void add(long id) {
        Optional<Chat> chat = findById(id);
        if (chat.isPresent()) {
            throw new ChatAlreadyExistsException("The chat is already registered!");
        }
        jpaChatRepository.save(new Chat(id));
    }

    @Override @Transactional public int remove(long id) {
        jpaChatRepository.deleteLinksByChatId(id);

        return jpaChatRepository.deleteChatEntityById(id);
    }

    @Override @Transactional public List<Chat> findAll() {

        return jpaChatRepository.findAll();
    }

    @Override @Transactional public Optional<Chat> findById(long id) {
        return jpaChatRepository.findById(id);
    }
}
