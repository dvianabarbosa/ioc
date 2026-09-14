package step7;

/**
 * Concrete creator whose product is not known until runtime.
 *
 * The account type comes from configuration. The creator turns that
 * setting into the right implementation, so neither the products nor the
 * client ever see the decision.
 */
public class TelegramCreator extends MessengerCreator {

    public static final String PERSONAL = "personal";
    public static final String BUSINESS = "business";

    private final String accountType;

    public TelegramCreator(String accountType) {
        this(accountType, DEFAULT_MAX_ATTEMPTS);
    }

    public TelegramCreator(String accountType, int maxAttempts) {
        super(maxAttempts);
        if (accountType == null
                || !(PERSONAL.equalsIgnoreCase(accountType) || BUSINESS.equalsIgnoreCase(accountType))) {
            throw new IllegalArgumentException(
                    "Unknown Telegram account type: " + accountType + " (expected personal or business)");
        }
        this.accountType = accountType.toLowerCase();
    }

    @Override
    protected Messenger createMessenger() {
        return BUSINESS.equals(accountType) ? new TelegramBusiness() : new Telegram();
    }
}
