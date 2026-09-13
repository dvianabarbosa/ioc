package step5;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Messenger telegram = new Telegram();
        Messenger whatsapp = new WhatsApp();
        MessengerService messengerService = new MessengerService(List.of(telegram, whatsapp));

        messengerService.sendMessage("Hello via MessengerService");
    }
}
