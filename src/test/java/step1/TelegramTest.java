package step1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TelegramTest {

    @Test
    void sendMessage_printsTelegramChannelAndMessage() {
        String output = StdoutCapture.capture(() -> new Telegram().sendMessage("hello"));

        assertEquals("Sending message via Telegram: hello" + System.lineSeparator(), output);
    }
}
