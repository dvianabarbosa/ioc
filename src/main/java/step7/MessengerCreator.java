package step7;

/**
 * The Creator in the Factory Method pattern.
 *
 * It declares the factory method, createMessenger(), but does not implement
 * it: each subclass decides which concrete Messenger to produce. The creator
 * only programs against the Messenger interface.
 *
 * It also owns the shared delivery workflow, send(message): validate the
 * message, obtain a product through the factory method, deliver, and retry
 * on failure. None of that belongs in a product. A Telegram or WhatsApp
 * class should only know how to send once; how many times to try, and what
 * counts as a valid message, is policy, and policy lives here, written once
 * for every channel. That shared policy is why this is an abstract class
 * rather than an interface.
 */
public abstract class MessengerCreator {

    public static final int DEFAULT_MAX_ATTEMPTS = 3;

    private final int maxAttempts;

    protected MessengerCreator() {
        this(DEFAULT_MAX_ATTEMPTS);
    }

    protected MessengerCreator(int maxAttempts) {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be at least 1, was " + maxAttempts);
        }
        this.maxAttempts = maxAttempts;
    }

    /** How many delivery attempts send() makes before giving up. */
    public int maxAttempts() {
        return maxAttempts;
    }

    /** The factory method. Subclasses override it to pick the concrete product. */
    protected abstract Messenger createMessenger();

    /** The template method. Shared workflow that relies on the factory method. */
    public void send(String message) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("message must not be blank");
        }
        Messenger messenger = createMessenger();
        deliverWithRetry(messenger, message);
    }

    private void deliverWithRetry(Messenger messenger, String message) {
        MessengerException lastFailure = null;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                messenger.sendMessage(message);
                return;
            } catch (MessengerException e) {
                lastFailure = e;
            }
        }
        throw new MessengerException("Delivery failed after " + maxAttempts + " attempts", lastFailure);
    }
}
