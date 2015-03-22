package uk.co.gcwilliams.ldb.model.builder;

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.gcwilliams.ldb.model.PreviousCallingPoint;
import uk.co.gcwilliams.ldb.model.Station;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * The previous calling point builder tests
 *
 * Created by GWilliams on 22/03/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class PreviousCallingPointBuilderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private StationBuilder stationBuilder;

    @Mock
    private Station station;

    @Test
    public void testValidationStationIsNull() {

        // arrange
        PreviousCallingPointBuilder builder = new PreviousCallingPointBuilder()
            .setActualDepartureTime(DateTime.now())
            .setStandardDepartureTime(DateTime.now());

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationActualDepartureTimeIsNull() {

        // arrange
        PreviousCallingPointBuilder builder = new PreviousCallingPointBuilder()
                .setStation(stationBuilder)
                .setStandardDepartureTime(DateTime.now());

        // act
        PreviousCallingPoint callingPoint = builder.build();

        // assert
        assertThat(callingPoint, instanceOf(PreviousCallingPoint.class));
        assertThat(callingPoint.getActualDepartureTime(), notNullValue());
        assertThat(callingPoint.getActualDepartureTime().isPresent(), equalTo(false));
    }

    @Test
    public void testValidationStandardDepartureTimeIsNull() {

        // arrange
        PreviousCallingPointBuilder builder = new PreviousCallingPointBuilder()
                .setStation(stationBuilder)
                .setActualDepartureTime(DateTime.now());

        // act
        PreviousCallingPoint callingPoint = builder.build();

        // assert
        assertThat(callingPoint, instanceOf(PreviousCallingPoint.class));
        assertThat(callingPoint.getStandardDepartureTime(), notNullValue());
        assertThat(callingPoint.getStandardDepartureTime().isPresent(), equalTo(false));
    }

    @Test
    public void testBuilderCreatesPreviousCallingPoint() {

        // arrange
        when(stationBuilder.build()).thenReturn(station);
        PreviousCallingPointBuilder builder = new PreviousCallingPointBuilder()
                .setStation(stationBuilder)
                .setStandardDepartureTime(DateTime.now())
                .setActualDepartureTime(DateTime.now());

        // act
        PreviousCallingPoint callingPoint = builder.build();

        // assert
        assertThat(callingPoint, instanceOf(PreviousCallingPoint.class));
        assertThat(callingPoint.getStation(), instanceOf(Station.class));
        assertThat(callingPoint.getActualDepartureTime(), notNullValue());
        assertThat(callingPoint.getStandardDepartureTime(), notNullValue());
        assertThat(callingPoint.getActualDepartureTime().isPresent(), equalTo(true));
        assertThat(callingPoint.getStandardDepartureTime().isPresent(), equalTo(true));
    }
}
