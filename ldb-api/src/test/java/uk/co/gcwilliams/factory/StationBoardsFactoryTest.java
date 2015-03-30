package uk.co.gcwilliams.factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.hk2.annotations.Service;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.gcwilliams.ldb.service.StationBoards;
import uk.co.gcwilliams.ldb.stubs.LDBServiceSoap;
import uk.co.gcwilliams.ldb.stubs.Ldb;
import uk.co.gcwilliams.properties.Property;

import javax.inject.Singleton;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * The station boards factory tests
 *
 * Created by GWilliams on 30/03/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class StationBoardsFactoryTest {

    private final LDBServiceSoap ldbServiceSoap = new Ldb().getLDBServiceSoap12();

    @Mock
    private Property ldbKeyProperty;

    @Test
    public void isService() {

        // act
        Service service = StationBoardsFactory.class.getAnnotation(Service.class);

        // assert
        assertThat(service, instanceOf(Service.class));
    }

    @Test
    public void isSingleton() throws NoSuchMethodException {

        // act
        Singleton singleton = StationBoardsFactory.class.getMethod("provide").getAnnotation(Singleton.class);

        // assert
        assertThat(singleton, instanceOf(Singleton.class));
    }

    @Test
    public void providesService() {

        // arrange
        StationBoardsFactory factory = new StationBoardsFactory(ldbServiceSoap, ldbKeyProperty);

        // act
        StationBoards service = factory.provide();

        // assert
        assertThat(service, instanceOf(StationBoards.class));
    }
}
