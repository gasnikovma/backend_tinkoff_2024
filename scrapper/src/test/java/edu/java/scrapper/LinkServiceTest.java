package edu.java.scrapper;

import edu.java.models.dto.Chat;
import edu.java.models.dto.Link;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.ChatRepository;
import edu.java.repository.LinkRepository;
import edu.java.service.LinkService;
import edu.java.service.LinkServiceImpl;
import java.net.URI;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LinkServiceTest {
    LinkService linkService;
    ChatRepository chatRepository;
    LinkRepository linkRepository;
    URI uri,uri_1,uri_2;
    Link link,link_1,link_2;

    /*  @BeforeEach
    public void init() {
        uri = URI.create("https://github.com/gasnikovma");
        uri_1=URI.create("https://github.com/iceberg");
        uri_2=URI.create("https://github.com/place");
        chatRepository = mock(JdbcChatRepository.class);
        linkRepository = mock(JdbcLinkRepository.class);
        linkService = new LinkServiceImpl(linkRepository, chatRepository);
        link = new Link(100L, uri.toString(),OffsetDateTime.MIN,OffsetDateTime.MIN);
        link_1 = new Link(100L, uri_1.toString(),OffsetDateTime.MIN,OffsetDateTime.MIN);
        link_2 = new Link(100L, uri_2.toString(),OffsetDateTime.MIN,OffsetDateTime.MIN);
    }

    @Test
    @DisplayName("addLinkTest")
    public void addLinkTest() {
        when(chatRepository.findById(100L)).thenReturn(Optional.of(new Chat()));
        when(linkRepository.add(100L, String.valueOf(uri))).thenReturn(link);
        assertEquals(linkService.add(100L, uri),link);
        verify(chatRepository).findById(100L);
        verify(linkRepository).findByChatIdAndUrl(100L,uri.toString());
        verify(linkRepository).add(100L,uri.toString());

    }

    @Test
    @DisplayName("removeLinkTest")
    public void removeLinkTest(){
        when(chatRepository.findById(100L)).thenReturn(Optional.of(new Chat()));
        when(linkRepository.findByChatIdAndUrl(100L, uri.toString())).thenReturn(Optional.of(link));
        when(linkRepository.remove(100L, uri.toString())).thenReturn(link);
        Link removed = linkService.remove(100L,uri);
        assertEquals(removed,link);
        verify(chatRepository).findById(100L);
        verify(linkRepository).findByChatIdAndUrl(100L,uri.toString());
        verify(linkRepository).remove(100L,uri.toString());

    }
    @Test
    @DisplayName("getLinksTest")
    public void getLinksTest(){
        when(chatRepository.findById(100L)).thenReturn(Optional.of(new Chat()));
        when(linkRepository.findLinksByChatId(100L)).thenReturn(List.of(link,link_1,link_2));
        List<Link> links = linkService.allLinks(100L);
        assertEquals(links.size(),3);
        verify(chatRepository).findById(100L);
        verify(linkRepository).findLinksByChatId(100L);


    }*/
}
