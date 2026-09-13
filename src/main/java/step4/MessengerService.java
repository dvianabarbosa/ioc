package step4;

public class MessengerService {

    private final Messenger telegram;
    private final Messenger whatsapp;

    public MessengerService(Messenger telegram, Messenger whatsapp) {
        this.telegram = telegram;
        this.whatsapp = whatsapp;
    }

    public void sendMessage(String message) {
        telegram.sendMessage(message);
        whatsapp.sendMessage(message);
    }
}
