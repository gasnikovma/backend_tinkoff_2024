package edu.java.repository;

import edu.java.models.dto.Chat;
import java.util.List;
import java.util.Optional;

public interface ChatRepository {
    void add(long id);

    int remove(long id);

    List<Chat> findAll();

    Optional<Chat> findById(long id);
}
