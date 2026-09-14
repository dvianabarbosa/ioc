package step5;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessengerServiceFactoryTest {

    @Test
    void createDefault_buildsServiceBroadcastingToTelegramAndWhatsApp() {
        MessengerService service = MessengerServiceFactory.createDefault();

        String output = StdoutCapture.capture(() -> service.sendMessage("hello"));

        String expected = "Sending message via Telegram: hello" + System.lineSeparator()
                + "Sending message via WhatsApp: hello" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void create_wrapsGivenMessengers() {
        RecordingMessenger first = new RecordingMessenger();
        RecordingMessenger second = new RecordingMessenger();

        MessengerService service = MessengerServiceFactory.create(List.of(first, second));
        service.sendMessage("factory");

        assertEquals(List.of("factory"), first.received);
        assertEquals(List.of("factory"), second.received);
    }

    private static final class RecordingMessenger implements Messenger {
        private final java.util.List<String> received = new java.util.ArrayList<>();

        @Override
        public void sendMessage(String message) {
            received.add(message);
        }
    }
}
