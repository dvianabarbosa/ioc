package step6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WhatsAppTest {

    @Test
    void sendMessage_printsWhatsAppChannelAndMessage() {
        Messenger messenger = new WhatsApp();

        String output = StdoutCapture.capture(() -> messenger.sendMessage("hello"));

        assertEquals("Sending message via WhatsApp: hello" + System.lineSeparator(), output);
    }
}
