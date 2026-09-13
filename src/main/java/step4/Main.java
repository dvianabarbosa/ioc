package step4;

public class Main {

    public static void main(String[] args) {
        Messenger telegram = new Telegram();
        Messenger whatsapp = new WhatsApp();
        MessengerService messengerService = new MessengerService(telegram, whatsapp);

        messengerService.sendMessage("Hello via MessengerService");
    }
}
