package step1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WhatsAppTest {

    @Test
    void sendMessage_printsWhatsAppChannelAndMessage() {
        String output = StdoutCapture.capture(() -> new WhatsApp().sendMessage("hello"));

        assertEquals("Sending message via WhatsApp: hello" + System.lineSeparator(), output);
    }
}
