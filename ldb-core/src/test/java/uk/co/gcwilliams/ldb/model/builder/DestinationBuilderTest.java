package uk.co.gcwilliams.ldb.model.builder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.gcwilliams.ldb.model.Destination;
import uk.co.gcwilliams.ldb.model.Station;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * The destination builder tests
 *
 * Created by GWilliams on 22/03/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class DestinationBuilderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private StationBuilder stationBuilder;

    @Mock
    private Station station;

    @Test
    public void testValidationStationIsNull() {

        // arrange
        DestinationBuilder builder = new DestinationBuilder().setVia("abc");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testBuilderCreatesDestination() {

        // arrange
        when(stationBuilder.build()).thenReturn(station);
        DestinationBuilder builder = new DestinationBuilder();
        builder.setStation(stationBuilder).setVia("xyz");

        // act
        Destination destination = builder.build();

        // assert
        assertThat(destination, instanceOf(Destination.class));
        assertThat(destination.getStation(), instanceOf(Station.class));
        assertThat(destination.getVia().isPresent(), equalTo(true));
        assertThat(destination.getVia().get(), equalTo("xyz"));
    }
}