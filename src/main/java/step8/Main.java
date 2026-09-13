package step8;

import java.util.List;

public class Main {

    /** JVM property that selects the Telegram account type, e.g. -Dtelegram.account=business */
    public static final String TELEGRAM_ACCOUNT_PROPERTY = "telegram.account";

    /** JVM property that sets how many delivery attempts each creator makes, e.g. -Dmessenger.maxAttempts=5 */
    public static final String MAX_ATTEMPTS_PROPERTY = "messenger.maxAttempts";

    public static void main(String[] args) {
        String telegramAccount = System.getProperty(TELEGRAM_ACCOUNT_PROPERTY, TelegramCreator.PERSONAL);
        int maxAttempts = Integer.getInteger(MAX_ATTEMPTS_PROPERTY, MessengerCreator.DEFAULT_MAX_ATTEMPTS);

        List<MessengerCreator> creators = List.of(
                new TelegramCreator(telegramAccount, maxAttempts),
                new WhatsAppCreator(maxAttempts));

        for (MessengerCreator creator : creators) {
            creator.send("Hello via factory method");
        }
    }
}
