package step7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {

    @Test
    void main_withoutConfiguration_usesPersonalTelegramAndWhatsApp() {
        String output = withProperties(() -> StdoutCapture.capture(() -> Main.main(new String[] {})));

        String expected = "Sending message via Telegram: Hello via factory method" + System.lineSeparator()
                + "Sending message via WhatsApp: Hello via factory method" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void main_withBusinessAccountConfigured_usesTelegramBusiness() {
        String output = withProperties(() -> StdoutCapture.capture(() -> Main.main(new String[] {})),
                Main.TELEGRAM_ACCOUNT_PROPERTY, "business");

        String expected = "Sending message via Telegram Business: Hello via factory method" + System.lineSeparator()
                + "Sending message via WhatsApp: Hello via factory method" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void main_withInvalidMaxAttemptsConfigured_failsFast() {
        withProperties(() -> {
            assertThrows(IllegalArgumentException.class, () -> Main.main(new String[] {}));
            return null;
        }, Main.MAX_ATTEMPTS_PROPERTY, "0");
    }

    @Test
    void main_withUnparsableMaxAttempts_fallsBackToDefaultAndStillSends() {
        String output = withProperties(() -> StdoutCapture.capture(() -> Main.main(new String[] {})),
                Main.MAX_ATTEMPTS_PROPERTY, "many");

        String expected = "Sending message via Telegram: Hello via factory method" + System.lineSeparator()
                + "Sending message via WhatsApp: Hello via factory method" + System.lineSeparator();
        assertEquals(expected, output);
    }

    /** Runs {@code action} with the given property key/value pairs set, restoring the previous values afterwards. */
    private static <T> T withProperties(java.util.function.Supplier<T> action, String... keyValues) {
        String[] keys = {Main.TELEGRAM_ACCOUNT_PROPERTY, Main.MAX_ATTEMPTS_PROPERTY};
        java.util.Map<String, String> previous = new java.util.HashMap<>();
        for (String key : keys) {
            previous.put(key, System.getProperty(key));
            System.clearProperty(key);
        }
        for (int i = 0; i < keyValues.length; i += 2) {
            System.setProperty(keyValues[i], keyValues[i + 1]);
        }
        try {
            return action.get();
        } finally {
            for (String key : keys) {
                if (previous.get(key) == null) {
                    System.clearProperty(key);
                } else {
                    System.setProperty(key, previous.get(key));
                }
            }
        }
    }
}
