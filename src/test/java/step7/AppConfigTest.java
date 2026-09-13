package step7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class AppConfigTest {

    @Test
    void configure_resolvesServiceBroadcastingToTelegramAndWhatsApp() {
        Container container = AppConfig.configure();

        MessengerService service = container.resolve(MessengerService.class);
        String output = StdoutCapture.capture(() -> service.sendMessage("hello"));

        String expected = "Sending message via Telegram: hello" + System.lineSeparator()
                + "Sending message via WhatsApp: hello" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void configure_serviceIsASingletonWithinTheContainer() {
        Container container = AppConfig.configure();

        assertSame(container.resolve(MessengerService.class), container.resolve(MessengerService.class));
    }

    @Test
    void configure_eachCallBuildsAnIndependentContainer() {
        Container first = AppConfig.configure();
        Container second = AppConfig.configure();

        assertEquals(false, first.resolve(MessengerService.class) == second.resolve(MessengerService.class));
    }
}
