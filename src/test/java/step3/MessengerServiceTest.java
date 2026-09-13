package step3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessengerServiceTest {

    @Test
    void sendMessage_delegatesToHardWiredMessengers() {
        MessengerService service = new MessengerService();

        String output = StdoutCapture.capture(() -> service.sendMessage("hello"));

        // No dependency injection: service always uses its field-created Telegram and WhatsApp instances.
        String expected = "Sending message via Telegram: hello" + System.lineSeparator()
                + "Sending message via WhatsApp: hello" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void sendMessage_forwardsExactMessage() {
        MessengerService service = new MessengerService();

        String output = StdoutCapture.capture(() -> service.sendMessage("Hello from Service"));

        String expected = "Sending message via Telegram: Hello from Service" + System.lineSeparator()
                + "Sending message via WhatsApp: Hello from Service" + System.lineSeparator();
        assertEquals(expected, output);
    }
}
