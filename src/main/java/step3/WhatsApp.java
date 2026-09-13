package step3;

public class WhatsApp implements Messenger {

    @Override
    public void sendMessage(String message) {
        System.out.println("Sending message via WhatsApp: " + message);
    }
}
