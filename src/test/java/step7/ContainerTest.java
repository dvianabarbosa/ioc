package step7;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContainerTest {

    @Test
    void resolve_returnsInstanceBuiltByRegisteredProvider() {
        Container container = new Container();
        container.register(Telegram.class, Telegram::new);

        Telegram telegram = container.resolve(Telegram.class);

        String output = StdoutCapture.capture(() -> telegram.sendMessage("hi"));
        assertEquals("Sending message via Telegram: hi" + System.lineSeparator(), output);
    }

    @Test
    void resolve_returnsSameInstanceOnEveryCall() {
        Container container = new Container();
        container.register(Telegram.class, Telegram::new);

        Telegram first = container.resolve(Telegram.class);
        Telegram second = container.resolve(Telegram.class);

        assertSame(first, second);
    }

    @Test
    void resolve_doesNotInvokeProviderUntilFirstResolve() {
        Container container = new Container();
        int[] calls = {0};
        container.register(Telegram.class, () -> {
            calls[0]++;
            return new Telegram();
        });

        assertEquals(0, calls[0]);
        container.resolve(Telegram.class);
        container.resolve(Telegram.class);
        assertEquals(1, calls[0]);
    }

    @Test
    void resolve_unregisteredType_throwsWithTypeName() {
        Container container = new Container();

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> container.resolve(Telegram.class));

        assertTrue(ex.getMessage().contains("Telegram"), ex.getMessage());
    }

    @Test
    void resolveAll_returnsEveryRegisteredImplementationInRegistrationOrder() {
        Container container = new Container();
        container.register(WhatsApp.class, WhatsApp::new);
        container.register(Telegram.class, Telegram::new);

        List<Messenger> messengers = container.resolveAll(Messenger.class);

        assertEquals(2, messengers.size());
        assertTrue(messengers.get(0) instanceof WhatsApp);
        assertTrue(messengers.get(1) instanceof Telegram);
    }

    @Test
    void resolveAll_reusesSingletonsCreatedByResolve() {
        Container container = new Container();
        container.register(Telegram.class, Telegram::new);

        Telegram direct = container.resolve(Telegram.class);
        List<Messenger> all = container.resolveAll(Messenger.class);

        assertSame(direct, all.get(0));
    }

    @Test
    void resolveAll_withNoMatchingRegistration_returnsEmptyList() {
        Container container = new Container();
        container.register(MessengerService.class, () -> new MessengerService(List.of()));

        List<Messenger> messengers = container.resolveAll(Messenger.class);

        assertTrue(messengers.isEmpty());
    }

    @Test
    void register_sameTypeTwice_throws() {
        Container container = new Container();
        container.register(Telegram.class, Telegram::new);

        assertThrows(IllegalStateException.class, () -> container.register(Telegram.class, Telegram::new));
    }
}
