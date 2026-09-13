package step8;

/** Thrown by a Messenger when a delivery attempt fails. The creator may retry it. */
public class MessengerException extends RuntimeException {

    public MessengerException(String message) {
        super(message);
    }

    public MessengerException(String message, Throwable cause) {
        super(message, cause);
    }
}
