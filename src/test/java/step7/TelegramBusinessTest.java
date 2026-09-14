package step7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TelegramBusinessTest {

    @Test
    void sendMessage_printsTelegramBusinessChannelAndMessage() {
        Messenger messenger = new TelegramBusiness();

        String output = StdoutCapture.capture(() -> messenger.sendMessage("hello"));

        assertEquals("Sending message via Telegram Business: hello" + System.lineSeparator(), output);
    }
}
