package edu.java.service;

import edu.java.models.dto.Link;
import java.net.URI;
import java.util.List;

public interface LinkService {
    Link add(long chatId, URI url);
    Link remove(long ChatId,URI url);
    List<Link> allLinks(long chatId);
}
