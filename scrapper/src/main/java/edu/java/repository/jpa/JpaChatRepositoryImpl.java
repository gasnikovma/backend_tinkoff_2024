package edu.java.repository.jpa;

import edu.java.exceptions.NoChatException;
import edu.java.models.dto.Chat;
import edu.java.repository.ChatRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor @Service public class JpaChatRepositoryImpl implements ChatRepository {

    private final JpaChatRepository jpaChatRepository;

    @Override @Transactional public void add(long id) {
        Optional<edu.java.models.dto.Chat> chat = findById(id);
        if (chat.isPresent()) {
            throw new NoChatException("The chat is not registered!");
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
