package uk.co.gcwilliams.ldb.model.builder;

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.gcwilliams.ldb.model.PreviousCallingPoint;
import uk.co.gcwilliams.ldb.model.ServiceDetail;
import uk.co.gcwilliams.ldb.model.Station;
import uk.co.gcwilliams.ldb.model.SubsequentCallingPoint;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * The service detail builder tests
 *
 * Created by GWilliams on 22/03/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceDetailBuilderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private StationBuilder stationBuilder;

    @Mock
    private Station station;

    @Mock
    private PreviousCallingPointBuilder previousBuilder;

    @Mock
    private PreviousCallingPoint previous;

    @Mock
    private SubsequentCallingPointBuilder subsequentBuilder;

    @Mock
    private SubsequentCallingPoint subsequent;

    @Test
    public void testValidationCurrentStationIsNull() {

        // arrange
        ServiceDetailBuilder builder = new ServiceDetailBuilder()
            .setGeneratedAt(DateTime.now())
            .setOperator("Southern")
            .setOperatorCode("STH")
            .addPreviousCallingPoints(singletonList(previousBuilder))
            .addSubsequentCallingPoints(singletonList(subsequentBuilder));

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationGeneratedAtIsNull() {

        // arrange
        ServiceDetailBuilder builder = new ServiceDetailBuilder()
                .setCurrentStation(stationBuilder)
                .setOperator("Southern")
                .setOperatorCode("STH")
                .addPreviousCallingPoints(singletonList(previousBuilder))
                .addSubsequentCallingPoints(singletonList(subsequentBuilder));

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationOperatorIsNull() {

        // arrange
        ServiceDetailBuilder builder = new ServiceDetailBuilder()
            .setCurrentStation(stationBuilder)
            .setGeneratedAt(DateTime.now())
            .setOperatorCode("STH")
            .addPreviousCallingPoints(singletonList(previousBuilder))
            .addSubsequentCallingPoints(singletonList(subsequentBuilder));

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationOperatorIsEmpty() {

        // arrange
        ServiceDetailBuilder builder = new ServiceDetailBuilder()
            .setCurrentStation(stationBuilder)
            .setGeneratedAt(DateTime.now())
            .setOperator("")
            .setOperatorCode("STH")
            .addPreviousCallingPoints(singletonList(previousBuilder))
            .addSubsequentCallingPoints(singletonList(subsequentBuilder));

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationOperatorCodeIsNull() {

        // arrange
        ServiceDetailBuilder builder = new ServiceDetailBuilder()
            .setCurrentStation(stationBuilder)
            .setGeneratedAt(DateTime.now())
            .setOperatorCode("Southern")
            .addPreviousCallingPoints(singletonList(previousBuilder))
            .addSubsequentCallingPoints(singletonList(subsequentBuilder));

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationOperatorCodeIsEmpty() {

        // arrange
        ServiceDetailBuilder builder = new ServiceDetailBuilder()
            .setCurrentStation(stationBuilder)
            .setGeneratedAt(DateTime.now())
            .setOperator("Southern")
            .setOperatorCode("")
            .addPreviousCallingPoints(singletonList(previousBuilder))
            .addSubsequentCallingPoints(singletonList(subsequentBuilder));

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationPreviousCallingPointsAreNull() {

        // arrange
        ServiceDetailBuilder builder = new ServiceDetailBuilder()
            .setCurrentStation(stationBuilder)
            .setGeneratedAt(DateTime.now())
            .setOperator("Southern")
            .setOperatorCode("STH")
            .addPreviousCallingPoints(singletonList(previousBuilder))
            .setSubsequentCallingPoints(null);

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testBuilderCreatesServiceDetail() {

        // arrange
        when(stationBuilder.build()).thenReturn(station);
        when(previousBuilder.build()).thenReturn(previous);
        when(subsequentBuilder.build()).thenReturn(subsequent);
        ServiceDetailBuilder builder = new ServiceDetailBuilder()
            .setCurrentStation(stationBuilder)
            .setGeneratedAt(DateTime.now())
            .setOperator("Southern")
            .setOperatorCode("STH")
            .addPreviousCallingPoints(singletonList(previousBuilder))
            .addSubsequentCallingPoints(singletonList(subsequentBuilder))
            .setPlatform(10)
            .setEstimatedTimeOfDeparture(DateTime.now())
            .setStandardTimeOfDeparture(DateTime.now())
            .setEstimatedTimeOfArrival(DateTime.now())
            .setStandardTimeOfArrival(DateTime.now());

        // act
        ServiceDetail detail = builder.build();

        // assert
        assertThat(detail, instanceOf(ServiceDetail.class));
        assertThat(detail.getCurrentStation(), instanceOf(Station.class));
        assertThat(detail.getGeneratedAt(), instanceOf(DateTime.class));
        assertThat(detail.getOperator(), equalTo("Southern"));
        assertThat(detail.getOperatorCode(), equalTo("STH"));
        assertThat(detail.getPreviousCallingPoints().size(), equalTo(1));
        assertThat(detail.getPreviousCallingPoints().get(0).size(), equalTo(1));
        assertThat(detail.getPreviousCallingPoints().get(0).get(0), equalTo(previous));
        assertThat(detail.getSubsequentCallingPoints().size(), equalTo(1));
        assertThat(detail.getSubsequentCallingPoints().get(0).size(), equalTo(1));
        assertThat(detail.getSubsequentCallingPoints().get(0).get(0), equalTo(subsequent));
        assertThat(detail.getEstimatedTimeOfDeparture(), notNullValue());
        assertThat(detail.getEstimatedTimeOfDeparture().get(), instanceOf(DateTime.class));
        assertThat(detail.getStandardTimeOfDeparture(), notNullValue());
        assertThat(detail.getStandardTimeOfDeparture().get(), instanceOf(DateTime.class));
        assertThat(detail.getEstimatedTimeOfArrival(), notNullValue());
        assertThat(detail.getEstimatedTimeOfArrival().get(), instanceOf(DateTime.class));
        assertThat(detail.getStandardTimeOfArrival(), notNullValue());
        assertThat(detail.getStandardTimeOfArrival().get(), instanceOf(DateTime.class));
    }
}