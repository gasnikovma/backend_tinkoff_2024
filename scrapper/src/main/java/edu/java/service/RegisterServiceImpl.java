package edu.java.service;

import edu.java.exceptions.ChatAlreadyExistsException;
import edu.java.exceptions.NoChatException;
import edu.java.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService{
    private final ChatRepository chatRepository;
    @Override
    public void reigster(long chatId) {
        try {
            chatRepository.add(chatId);
        }
        catch (DataIntegrityViolationException e){
            throw new ChatAlreadyExistsException("Chat is already in db");
        }

    }

    @Override
    public void unregister(long chatId) {
        if(chatRepository.remove(chatId)==0){
            throw new NoChatException("Chat is not in db!");
        }

    }
}
