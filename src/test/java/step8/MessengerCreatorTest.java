package step8;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MessengerCreatorTest {

    @Test
    void send_usesProductReturnedByFactoryMethod() {
        RecordingMessenger product = new RecordingMessenger();
        MessengerCreator creator = new StubCreator(product);

        creator.send("hello");

        assertEquals(List.of("hello"), product.received);
    }

    @Test
    void send_callsFactoryMethodOnEveryInvocation() {
        StubCreator creator = new StubCreator(new RecordingMessenger());

        creator.send("one");
        creator.send("two");

        assertEquals(2, creator.factoryCalls);
    }

    @Test
    void send_blankMessage_rejectsBeforeCreatingProduct() {
        StubCreator creator = new StubCreator(new RecordingMessenger());

        assertThrows(IllegalArgumentException.class, () -> creator.send("   "));
        assertThrows(IllegalArgumentException.class, () -> creator.send(null));
        assertEquals(0, creator.factoryCalls);
    }

    @Test
    void send_retriesUntilDeliverySucceeds() {
        FlakyMessenger product = new FlakyMessenger(2);
        StubCreator creator = new StubCreator(product);

        creator.send("hello");

        assertEquals(3, product.attempts);
        assertEquals(List.of("hello"), product.received);
    }

    @Test
    void send_createsProductOncePerSendEvenWhenRetrying() {
        StubCreator creator = new StubCreator(new FlakyMessenger(2));

        creator.send("hello");

        assertEquals(1, creator.factoryCalls);
    }

    @Test
    void send_givesUpAfterMaxAttemptsAndKeepsLastFailureAsCause() {
        FlakyMessenger product = new FlakyMessenger(Integer.MAX_VALUE);
        StubCreator creator = new StubCreator(product, 3);

        MessengerException ex = assertThrows(MessengerException.class, () -> creator.send("hello"));

        assertEquals(3, product.attempts);
        assertSame(product.lastFailure, ex.getCause());
        assertEquals(List.of(), product.received);
    }

    @Test
    void send_doesNotRetryUnexpectedExceptions() {
        Messenger product = message -> {
            throw new IllegalStateException("bug in the product");
        };
        CountingCreator creator = new CountingCreator(product);

        assertThrows(IllegalStateException.class, () -> creator.send("hello"));
        assertEquals(1, creator.deliveries);
    }

    @Test
    void maxAttempts_reportsConfiguredLimit() {
        assertEquals(MessengerCreator.DEFAULT_MAX_ATTEMPTS, new StubCreator(new RecordingMessenger()).maxAttempts());
        assertEquals(7, new StubCreator(new RecordingMessenger(), 7).maxAttempts());
    }

    @Test
    void constructor_rejectsMaxAttemptsBelowOne() {
        assertThrows(IllegalArgumentException.class, () -> new StubCreator(new RecordingMessenger(), 0));
    }

    private static class StubCreator extends MessengerCreator {
        private final Messenger product;
        int factoryCalls;

        StubCreator(Messenger product) {
            this.product = product;
        }

        StubCreator(Messenger product, int maxAttempts) {
            super(maxAttempts);
            this.product = product;
        }

        @Override
        protected Messenger createMessenger() {
            factoryCalls++;
            return product;
        }
    }

    /** Wraps a product so the test can count how many times delivery was attempted. */
    private static final class CountingCreator extends MessengerCreator {
        private final Messenger product;
        int deliveries;

        CountingCreator(Messenger product) {
            this.product = product;
        }

        @Override
        protected Messenger createMessenger() {
            return message -> {
                deliveries++;
                product.sendMessage(message);
            };
        }
    }

    private static final class RecordingMessenger implements Messenger {
        private final List<String> received = new ArrayList<>();

        @Override
        public void sendMessage(String message) {
            received.add(message);
        }
    }

    /** Fails the first {@code failures} deliveries, then succeeds. */
    private static final class FlakyMessenger implements Messenger {
        private final int failures;
        private final List<String> received = new ArrayList<>();
        int attempts;
        MessengerException lastFailure;

        FlakyMessenger(int failures) {
            this.failures = failures;
        }

        @Override
        public void sendMessage(String message) {
            attempts++;
            if (attempts <= failures) {
                lastFailure = new MessengerException("attempt " + attempts + " failed");
                throw lastFailure;
            }
            received.add(message);
        }
    }
}
