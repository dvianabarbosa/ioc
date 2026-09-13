package step6;

public class Main {

    public static void main(String[] args) {
        MessengerService messengerService = MessengerServiceFactory.createDefault();

        messengerService.sendMessage("Hello via MessengerService");
    }
}
