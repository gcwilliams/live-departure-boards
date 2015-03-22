package uk.co.gcwilliams.ldb.model.builder;

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.gcwilliams.ldb.model.Station;
import uk.co.gcwilliams.ldb.model.SubsequentCallingPoint;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * The subsequent calling pointer builder tests
 *
 * Created by GWilliams on 22/03/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SubsequentCallingPointBuilderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private StationBuilder stationBuilder;

    @Mock
    private Station station;

    @Test
    public void testValidationStationIsNull() {

        // arrange
        SubsequentCallingPointBuilder builder = new SubsequentCallingPointBuilder()
            .setEstimatedDepartureTime(DateTime.now())
            .setStandardDepartureTime(DateTime.now());

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationEstimatedDepartureTimeIsNull() {

        // arrange
        SubsequentCallingPointBuilder builder = new SubsequentCallingPointBuilder()
                .setStation(stationBuilder)
                .setStandardDepartureTime(DateTime.now());

        // act
        SubsequentCallingPoint callingPoint = builder.build();

        // assert
        assertThat(callingPoint, instanceOf(SubsequentCallingPoint.class));
        assertThat(callingPoint.getEstimatedDepartureTime(), notNullValue());
        assertThat(callingPoint.getEstimatedDepartureTime().isPresent(), equalTo(false));
    }

    @Test
    public void testValidationStandardDepartureTimeIsNull() {

        // arrange
        SubsequentCallingPointBuilder builder = new SubsequentCallingPointBuilder()
                .setStation(stationBuilder)
                .setEstimatedDepartureTime(DateTime.now());

        // act
        SubsequentCallingPoint callingPoint = builder.build();

        // assert
        assertThat(callingPoint, instanceOf(SubsequentCallingPoint.class));
        assertThat(callingPoint.getStandardDepartureTime(), notNullValue());
        assertThat(callingPoint.getStandardDepartureTime().isPresent(), equalTo(false));
    }

    @Test
    public void testBuilderCreatesSubsequentCallingPoint() {

        // arrange
        when(stationBuilder.build()).thenReturn(station);
        SubsequentCallingPointBuilder builder = new SubsequentCallingPointBuilder()
                .setStation(stationBuilder)
                .setStandardDepartureTime(DateTime.now())
                .setEstimatedDepartureTime(DateTime.now());

        // act
        SubsequentCallingPoint callingPoint = builder.build();

        // assert
        assertThat(callingPoint, instanceOf(SubsequentCallingPoint.class));
        assertThat(callingPoint.getStation(), instanceOf(Station.class));
        assertThat(callingPoint.getEstimatedDepartureTime(), notNullValue());
        assertThat(callingPoint.getStandardDepartureTime(), notNullValue());
        assertThat(callingPoint.getEstimatedDepartureTime().isPresent(), equalTo(true));
        assertThat(callingPoint.getStandardDepartureTime().isPresent(), equalTo(true));
    }
}
