package uk.co.gcwilliams.injection;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.Binder;

import java.util.stream.Stream;

/**
 * The reflective binder, providers a binder which will bind
 * classes to HK2 based on JSR330 annotations
 *
 * Created by GWilliams on 30/03/2015.
 */
public abstract class ReflectiveBinder implements Binder {

    @Override
    @SuppressWarnings("unchecked")
    public final void bind(DynamicConfiguration configuration) {
        bind().forEach(configuration::addActiveDescriptor);
        bindFactories().forEach(c -> configuration.addActiveFactoryDescriptor((Class<? extends Factory<Object>>)c));
    }

    /**
     * Called to bind the classes for the application
     *
     * @return the stream of classes to bind
     */
    protected Stream<Class<?>> bind() {
        return Stream.empty();
    }

    /**
     * Called to bind any factories for the application
     *
     * @return the stream of classes to bind
     */
    protected Stream<Class<? extends Factory<?>>> bindFactories() {
        return Stream.empty();
    }
}
