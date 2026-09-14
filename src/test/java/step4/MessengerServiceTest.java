package step4;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessengerServiceTest {

    @Test
    void sendMessage_delegatesToAllInjectedMessengers() {
        MessengerService service = new MessengerService(List.of(new Telegram(), new WhatsApp()));

        String output = StdoutCapture.capture(() -> service.sendMessage("hello"));

        String expected = "Sending message via Telegram: hello" + System.lineSeparator()
                + "Sending message via WhatsApp: hello" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void sendMessage_forwardsExactMessageToEveryMessenger() {
        MessengerService service = new MessengerService(List.of(new Telegram(), new WhatsApp()));

        String output = StdoutCapture.capture(() -> service.sendMessage("Hello from Service"));

        String expected = "Sending message via Telegram: Hello from Service" + System.lineSeparator()
                + "Sending message via WhatsApp: Hello from Service" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void sendMessage_forwardsExactMessageToInjectedDependencies() {
        RecordingMessenger first = new RecordingMessenger();
        RecordingMessenger second = new RecordingMessenger();
        MessengerService service = new MessengerService(List.of(first, second));

        service.sendMessage("Hello from Service");

        assertEquals(List.of("Hello from Service"), first.received);
        assertEquals(List.of("Hello from Service"), second.received);
    }

    @Test
    void sendMessage_withEmptyList_sendsNothing() {
        MessengerService service = new MessengerService(List.of());

        String output = StdoutCapture.capture(() -> service.sendMessage("hello"));

        assertEquals("", output);
    }

    private static final class RecordingMessenger implements Messenger {
        private final List<String> received = new ArrayList<>();

        @Override
        public void sendMessage(String message) {
            received.add(message);
        }
    }
}
