package uk.co.gcwilliams.factory;

import org.junit.Test;
import org.jvnet.hk2.annotations.Service;
import uk.co.gcwilliams.ldb.stubs.LDBServiceSoap;

import javax.inject.Singleton;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * The LDB soap service factory tests
 *
 * Created by GWilliams on 30/03/2015.
 */
public class LdbSoapServiceFactoryTest {

    @Test
    public void isService() {

        // act
        Service service = LdbSoapServiceFactory.class.getAnnotation(Service.class);

        // assert
        assertThat(service, instanceOf(Service.class));
    }

    @Test
    public void isSingleton() throws NoSuchMethodException {

        // act
        Singleton singleton = LdbSoapServiceFactory.class.getMethod("provide").getAnnotation(Singleton.class);

        // assert
        assertThat(singleton, instanceOf(Singleton.class));
    }

    @Test
    public void providesService() {

        // arrange
        LdbSoapServiceFactory factory = new LdbSoapServiceFactory();

        // act
        LDBServiceSoap service = factory.provide();

        // assert
        assertThat(service, instanceOf(LDBServiceSoap.class));
    }
}
