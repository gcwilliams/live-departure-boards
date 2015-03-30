package uk.co.gcwilliams.factory;

import org.junit.Test;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Singleton;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * The station code reader factory tests
 *
 * Created by GWilliams on 30/03/2015.
 */
public class StationCodeReaderFactoryTest {

    @Test
    public void isService() {

        // act
        Service service = StationCodeReaderFactory.class.getAnnotation(Service.class);

        // assert
        assertThat(service, instanceOf(Service.class));
    }

    @Test
    public void isSingleton() throws NoSuchMethodException {

        // act
        Singleton singleton = StationCodeReaderFactory.class.getMethod("provide").getAnnotation(Singleton.class);

        // assert
        assertThat(singleton, instanceOf(Singleton.class));
    }
}
