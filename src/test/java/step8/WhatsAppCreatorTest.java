package step8;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WhatsAppCreatorTest {

    @Test
    void createMessenger_returnsWhatsApp() {
        Messenger product = new WhatsAppCreator().createMessenger();

        assertInstanceOf(WhatsApp.class, product);
    }

    @Test
    void createMessenger_reusesTheSameSessionAcrossCalls() {
        WhatsAppCreator creator = new WhatsAppCreator();

        assertSame(creator.createMessenger(), creator.createMessenger());
    }

    @Test
    void createMessenger_eachCreatorOwnsItsOwnSession() {
        assertNotSame(new WhatsAppCreator().createMessenger(), new WhatsAppCreator().createMessenger());
    }

    @Test
    void constructor_withoutMaxAttempts_usesDefault() {
        assertEquals(MessengerCreator.DEFAULT_MAX_ATTEMPTS, new WhatsAppCreator().maxAttempts());
    }

    @Test
    void constructor_withMaxAttempts_usesGivenValue() {
        assertEquals(5, new WhatsAppCreator(5).maxAttempts());
    }

    @Test
    void constructor_withMaxAttemptsBelowOne_throws() {
        assertThrows(IllegalArgumentException.class, () -> new WhatsAppCreator(0));
    }

    @Test
    void send_deliversThroughWhatsApp() {
        MessengerCreator creator = new WhatsAppCreator();

        String output = StdoutCapture.capture(() -> creator.send("hello"));

        assertEquals("Sending message via WhatsApp: hello" + System.lineSeparator(), output);
    }
}
