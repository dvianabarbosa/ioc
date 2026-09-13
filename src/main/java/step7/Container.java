package step7;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A minimal Inversion of Control container.
 *
 * Application classes never call "new" on their collaborators. Instead the
 * container is told how to build each type (register) and hands out fully
 * wired instances on request (resolve). Control over object creation is
 * inverted: it moves from the classes that use the objects to the container.
 *
 * This is the same idea a real framework (Spring, Guice, CDI) implements,
 * stripped down to the essentials: a registry of providers and a cache of
 * singletons.
 */
public class Container {

    private final Map<Class<?>, Supplier<?>> providers = new LinkedHashMap<>();
    private final Map<Class<?>, Object> singletons = new LinkedHashMap<>();

    /** Tell the container how to build {@code type}. Nothing is created yet. */
    public <T> void register(Class<T> type, Supplier<? extends T> provider) {
        if (providers.containsKey(type)) {
            throw new IllegalStateException("Type already registered: " + type.getName());
        }
        providers.put(type, provider);
    }

    /** Return the single instance of {@code type}, building it on first use. */
    public <T> T resolve(Class<T> type) {
        Supplier<?> provider = providers.get(type);
        if (provider == null) {
            throw new IllegalStateException("No registration for type: " + type.getName());
        }
        Object instance = singletons.get(type);
        if (instance == null) {
            instance = provider.get();
            singletons.put(type, instance);
        }
        return type.cast(instance);
    }

    /** Return every registered instance assignable to {@code type}, in registration order. */
    public <T> List<T> resolveAll(Class<T> type) {
        List<T> matches = new ArrayList<>();
        for (Class<?> registered : providers.keySet()) {
            if (type.isAssignableFrom(registered)) {
                matches.add(type.cast(resolve(registered)));
            }
        }
        return matches;
    }
}
