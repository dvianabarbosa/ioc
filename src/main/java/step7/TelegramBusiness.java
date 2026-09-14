package step7;

public class TelegramBusiness implements Messenger {

    @Override
    public void sendMessage(String message) {
        System.out.println("Sending message via Telegram Business: " + message);
    }
}
