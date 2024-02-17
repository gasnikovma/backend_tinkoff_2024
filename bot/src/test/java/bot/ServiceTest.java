package bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.Service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Field;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    @Mock TelegramBot bot;

    @InjectMocks
    Service botService;

    @Captor ArgumentCaptor<SendMessage> captor;

    @ParameterizedTest()
    @MethodSource("provider")
    public void testProcess(String command, String answer) throws NoSuchFieldException, IllegalAccessException {
        Update update = new Update();
        Message message = new Message();
        Chat chat = new Chat();

        Field messageProperty = message.getClass().getDeclaredField("text");
        messageProperty.setAccessible(true);
        messageProperty.set(message, command);

        Field chatProperty = chat.getClass().getDeclaredField("id");
        chatProperty.setAccessible(true);
        chatProperty.set(chat, 10L);

        Field messageMIMProperty = message.getClass().getSuperclass().getDeclaredField("chat");
        messageMIMProperty.setAccessible(true);
        messageMIMProperty.set(message, chat);

        Field updateProprety = update.getClass().getDeclaredField("message");
        updateProprety.setAccessible(true);
        updateProprety.set(update, message);

        botService.handleUpdates(update);
        verify(bot).execute(captor.capture());
        SendMessage sendMessage = captor.getValue();
        assertEquals(sendMessage.getParameters().get("text"), answer);

    }

    private static Stream<Arguments> provider() {
        return Stream.of(
            Arguments.of("/start", "User has been successfully registered"),
            Arguments.of("/track", "Please,enter the link you want to track"),
            Arguments.of("/untrack", "Enter the link from which you no longer want to receive updates"),
            Arguments.of("/list", "empty yet")
        );
    }
}
