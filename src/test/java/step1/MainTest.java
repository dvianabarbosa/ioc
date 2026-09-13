package step1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void main_initializesTelegramAndWhatsAppAndSendsMessages() {
        String output = StdoutCapture.capture(() -> Main.main(new String[] {}));

        String expected = "Sending message via Telegram: Hello from Telegram" + System.lineSeparator()
                + "Sending message via WhatsApp: Hello from WhatsApp" + System.lineSeparator();
        assertEquals(expected, output);
    }
}
