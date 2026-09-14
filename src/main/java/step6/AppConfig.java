package step6;

/**
 * The composition root: the one place in the application that knows which
 * concrete classes exist and how they fit together.
 *
 * Compare with step5, where MessengerServiceFactory built the graph by hand.
 * Here we only describe the graph; the Container decides when to build it.
 * Adding a new channel means adding one register(...) line and nothing else:
 * MessengerService picks it up through resolveAll(Messenger.class).
 */
public final class AppConfig {

    private AppConfig() {
    }

    public static Container configure() {
        Container container = new Container();

        container.register(Telegram.class, Telegram::new);
        container.register(WhatsApp.class, WhatsApp::new);
        container.register(MessengerService.class,
                () -> new MessengerService(container.resolveAll(Messenger.class)));

        return container;
    }
}
