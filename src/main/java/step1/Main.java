package step1;

public class Main {

    public static void main(String[] args) {
        Telegram telegram = new Telegram();
        WhatsApp whatsApp = new WhatsApp();

        telegram.sendMessage("Hello from Telegram");
        whatsApp.sendMessage("Hello from WhatsApp");
    }
}
