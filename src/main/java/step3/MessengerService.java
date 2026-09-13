package step3;

public class MessengerService {

    // Tight coupling: concrete dependencies created in the field, no dependency injection.
    private Messenger telegram = new Telegram();
    private Messenger whatsapp = new WhatsApp();

    public void sendMessage(String message) {
        telegram.sendMessage(message);
        whatsapp.sendMessage(message);
    }
}
