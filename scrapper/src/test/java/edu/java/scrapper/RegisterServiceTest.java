package edu.java.scrapper;

import edu.java.exceptions.ChatAlreadyExistsException;
import edu.java.jdbc.JdbcChatRepository;
import edu.java.repository.ChatRepository;
import edu.java.service.RegisterService;
import edu.java.service.RegisterServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RegisterServiceTest {
    @Test
    @DisplayName("registerTest")
    public void registerTest() {
        ChatRepository chatRepository = mock(JdbcChatRepository.class);
        RegisterService registerService = new RegisterServiceImpl(chatRepository);
        registerService.reigster(100L);
        verify(chatRepository).add(100L);
        registerService.reigster(100L);
        doThrow(new DataIntegrityViolationException("")).when(chatRepository).add(100L);
        try {
            registerService.reigster(100L);

        } catch (ChatAlreadyExistsException e) {
            assertThat("Chat is already in db").isEqualTo(e.getMessage());
        }

    }

}
