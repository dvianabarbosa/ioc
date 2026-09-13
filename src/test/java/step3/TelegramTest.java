package step3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TelegramTest {

    @Test
    void sendMessage_printsTelegramChannelAndMessage() {
        Messenger messenger = new Telegram();

        String output = StdoutCapture.capture(() -> messenger.sendMessage("hello"));

        assertEquals("Sending message via Telegram: hello" + System.lineSeparator(), output);
    }
}
