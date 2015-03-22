package uk.co.gcwilliams.ldb.model.builder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import uk.co.gcwilliams.ldb.model.Station;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * The station builder tests
 *
 * Created by GWilliams on 22/03/2015.
 */
@RunWith(JUnit4.class)
public class StationBuilderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testValidationStationNameIsNull() {

        // arrange
        StationBuilder builder = new StationBuilder().setStationId("abc");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationStationNameIsEmpty() {

        // arrange
        StationBuilder builder = new StationBuilder().setStationId("abc").setName("");


        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationStationIdIsNull() {

        // arrange
        StationBuilder builder = new StationBuilder().setName("abc");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationStationIdIsEmpty() {

        // arrange
        StationBuilder builder = new StationBuilder().setName("abc").setStationId("");


        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testBuilderCreatesStation() {

        // arrange
        StationBuilder builder = new StationBuilder().setName("abc").setStationId("123");

        // act
        Station station = builder.build();

        // assert
        assertThat(station, instanceOf(Station.class));
        assertThat(station.getName(), equalTo("abc"));
        assertThat(station.getStationId().get(), equalTo("123"));
    }
}