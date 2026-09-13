package step7;

public class Main {

    public static void main(String[] args) {
        Container container = AppConfig.configure();
        MessengerService messengerService = container.resolve(MessengerService.class);

        messengerService.sendMessage("Hello via MessengerService");
    }
}
