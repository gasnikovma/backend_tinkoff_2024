package edu.java.service;

import edu.java.exceptions.LinkAlreadyExistsException;
import edu.java.exceptions.NoChatException;
import edu.java.exceptions.NoLinkException;
import edu.java.models.dto.Link;
import edu.java.repository.ChatRepository;
import edu.java.repository.LinkRepository;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;
    private final ChatRepository chatRepository;
    private final String noChatExceptionMessage = "This chat is not registered!";

    @Override public Link add(long chatId, URI url) {
        if (chatRepository.findById(chatId).isEmpty()) {
            throw new NoChatException(noChatExceptionMessage);
        }
        Optional<Link> link = linkRepository.findByChatIdAndUrl(chatId, url.toString());
        if (link.isPresent()) {
            throw new LinkAlreadyExistsException("This link is already in db");
        }
        return linkRepository.add(chatId, url.toString());
    }

    @Override public Link remove(long chatId, URI url) {
        if (chatRepository.findById(chatId).isEmpty()) {
            throw new NoChatException(noChatExceptionMessage);
        }
        Optional<Link> link = linkRepository.findByChatIdAndUrl(chatId, url.toString());
        if (link.isEmpty()) {
            throw new NoLinkException("No such link in database!");
        }
        return linkRepository.remove(chatId, url.toString());
    }

    @Override public List<Link> allLinks(long chatId) {
        if (chatRepository.findById(chatId).isEmpty()) {
            throw new NoChatException(noChatExceptionMessage);
        }
        return linkRepository.findLinksByChatId(chatId);
    }
}
