package step3;

import java.util.List;

public class MessengerService {

    // Tight coupling: concrete dependencies created in the field, no dependency injection.
    private final List<Messenger> messengers = List.of(new Telegram(), new WhatsApp());

    public void sendMessage(String message) {
        for (Messenger messenger : messengers) {
            messenger.sendMessage(message);
        }
    }
}
