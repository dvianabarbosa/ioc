package step4;

import java.util.List;

public class MessengerService {

    private final List<Messenger> messengers;

    public MessengerService(Messenger telegram, Messenger whatsapp) {
        this.messengers = List.of(telegram, whatsapp);
    }

    public void sendMessage(String message) {
        for (Messenger messenger : messengers) {
            messenger.sendMessage(message);
        }
    }
}
