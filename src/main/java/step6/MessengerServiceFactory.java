package step6;

import java.util.List;

public class MessengerServiceFactory {

    public static MessengerService createDefault() {
        return new MessengerService(List.of(new Telegram(), new WhatsApp()));
    }

    public static MessengerService create(List<Messenger> messengers) {
        return new MessengerService(messengers);
    }
}
