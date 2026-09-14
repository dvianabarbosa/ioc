package step2;

public class Main {

    public static void main(String[] args) {
        Messenger telegram = new Telegram();
        Messenger whatsApp = new WhatsApp();

        telegram.sendMessage("Hello from Telegram");
        whatsApp.sendMessage("Hello from WhatsApp");
    }
}
