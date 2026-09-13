package step6;

public class Telegram implements Messenger {

    @Override
    public void sendMessage(String message) {
        System.out.println("Sending message via Telegram: " + message);
    }
}
