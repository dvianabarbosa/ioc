package step7;

import java.util.List;

public class MessengerService {

    private final List<Messenger> messengers;

    public MessengerService(List<Messenger> messengers) {
        this.messengers = messengers;
    }

    public void sendMessage(String message) {
        for (Messenger messenger : messengers) {
            messenger.sendMessage(message);
        }
    }
}
