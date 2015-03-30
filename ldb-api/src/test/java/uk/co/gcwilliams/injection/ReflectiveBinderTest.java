package uk.co.gcwilliams.injection;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.stream.Stream;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * The reflective binder tests
 *
 * Created by GWilliams on 30/03/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReflectiveBinderTest {

    @Mock
    private DynamicConfiguration configuration;

    @Test
    public void testBindObjects() {

        // arrange
        ReflectiveBinder binder = new ReflectiveBinder() {
            @Override
            protected Stream<Class<?>> bind() {
                return Stream.of(Object.class);
            }
        };

        // act
        binder.bind(configuration);

        // assert
        verify(configuration).addActiveDescriptor(eq(Object.class));
    }

    @Test
    public void testBindFactories() {

        // arrange
        ReflectiveBinder binder = new ReflectiveBinder() {
            @Override
            protected Stream<Class<? extends Factory<?>>> bindFactories() {
                return Stream.of(FactoryClass.class);
            }
        };

        // act
        binder.bind(configuration);

        // assert
        verify(configuration).addActiveFactoryDescriptor(eq(FactoryClass.class));
    }

    private static class FactoryClass implements Factory<Object> {

        @Override
        public Object provide() {
            return null;
        }

        @Override
        public void dispose(Object instance) { }
    }
}
