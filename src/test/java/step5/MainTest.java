package step5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void main_usesFactoryToCreateServiceAndSendsMessage() {
        String output = StdoutCapture.capture(() -> Main.main(new String[] {}));

        String expected = "Sending message via Telegram: Hello via MessengerService" + System.lineSeparator()
                + "Sending message via WhatsApp: Hello via MessengerService" + System.lineSeparator();
        assertEquals(expected, output);
    }
}
