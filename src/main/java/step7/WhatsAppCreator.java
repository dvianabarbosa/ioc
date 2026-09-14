package step7;

/**
 * Concrete creator that controls the product's lifecycle.
 *
 * Opening a WhatsApp session is treated as expensive, so the creator opens
 * it once and hands the same instance back on every call. The product does
 * not know it is being reused; that decision belongs to the creator.
 */
public class WhatsAppCreator extends MessengerCreator {

    private Messenger session;

    public WhatsAppCreator() {
        this(DEFAULT_MAX_ATTEMPTS);
    }

    public WhatsAppCreator(int maxAttempts) {
        super(maxAttempts);
    }

    @Override
    protected Messenger createMessenger() {
        if (session == null) {
            session = new WhatsApp();
        }
        return session;
    }
}
