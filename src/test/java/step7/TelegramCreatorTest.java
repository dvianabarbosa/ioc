package step7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TelegramCreatorTest {

    @Test
    void createMessenger_personalAccount_returnsTelegram() {
        Messenger product = new TelegramCreator("personal").createMessenger();

        assertInstanceOf(Telegram.class, product);
    }

    @Test
    void createMessenger_businessAccount_returnsTelegramBusiness() {
        Messenger product = new TelegramCreator("business").createMessenger();

        assertInstanceOf(TelegramBusiness.class, product);
    }

    @Test
    void createMessenger_accountTypeIsCaseInsensitive() {
        assertInstanceOf(TelegramBusiness.class, new TelegramCreator("BUSINESS").createMessenger());
    }

    @Test
    void constructor_unknownAccountType_throws() {
        assertThrows(IllegalArgumentException.class, () -> new TelegramCreator("enterprise"));
        assertThrows(IllegalArgumentException.class, () -> new TelegramCreator(null));
    }

    @Test
    void constructor_withoutMaxAttempts_usesDefault() {
        assertEquals(MessengerCreator.DEFAULT_MAX_ATTEMPTS, new TelegramCreator("personal").maxAttempts());
    }

    @Test
    void constructor_withMaxAttempts_usesGivenValue() {
        assertEquals(5, new TelegramCreator("business", 5).maxAttempts());
    }

    @Test
    void constructor_withMaxAttemptsBelowOne_throws() {
        assertThrows(IllegalArgumentException.class, () -> new TelegramCreator("personal", 0));
    }

    @Test
    void send_deliversThroughSelectedImplementation() {
        MessengerCreator creator = new TelegramCreator("business");

        String output = StdoutCapture.capture(() -> creator.send("hello"));

        assertEquals("Sending message via Telegram Business: hello" + System.lineSeparator(), output);
    }
}
