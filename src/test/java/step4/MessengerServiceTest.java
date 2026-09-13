package step4;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessengerServiceTest {

    @Test
    void sendMessage_delegatesToInjectedTelegramAndWhatsApp() {
        MessengerService service = new MessengerService(new Telegram(), new WhatsApp());

        String output = StdoutCapture.capture(() -> service.sendMessage("hello"));

        String expected = "Sending message via Telegram: hello" + System.lineSeparator()
                + "Sending message via WhatsApp: hello" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void sendMessage_forwardsExactMessageToBothDependencies() {
        MessengerService service = new MessengerService(new Telegram(), new WhatsApp());

        String output = StdoutCapture.capture(() -> service.sendMessage("Hello from Service"));

        String expected = "Sending message via Telegram: Hello from Service" + System.lineSeparator()
                + "Sending message via WhatsApp: Hello from Service" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void sendMessage_usesConstructorInjectedDoublesInsteadOfConcreteClasses() {
        RecordingMessenger first = new RecordingMessenger();
        RecordingMessenger second = new RecordingMessenger();
        MessengerService service = new MessengerService(first, second);

        service.sendMessage("injected");

        assertEquals(List.of("injected"), first.received);
        assertEquals(List.of("injected"), second.received);
    }

    private static final class RecordingMessenger implements Messenger {
        private final List<String> received = new ArrayList<>();

        @Override
        public void sendMessage(String message) {
            received.add(message);
        }
    }
}
