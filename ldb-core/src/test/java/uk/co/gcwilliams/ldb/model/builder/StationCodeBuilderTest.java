package uk.co.gcwilliams.ldb.model.builder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import uk.co.gcwilliams.ldb.model.StationCode;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * The station code builder tests
 *
 * Created by GWilliams on 22/03/2015.
 */
@RunWith(JUnit4.class)
public class StationCodeBuilderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testValidationNameIsNull() {

        // arrange
        StationCodeBuilder builder = new StationCodeBuilder().setStationId("123");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationNameIsEmpty() {

        // arrange
        StationCodeBuilder builder = new StationCodeBuilder().setStationId("123").setName("");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationIdIsNull() {

        // arrange
        StationCodeBuilder builder = new StationCodeBuilder().setName("abc");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationIdIsEmpty() {

        // arrange
        StationCodeBuilder builder = new StationCodeBuilder().setName("123").setStationId("");

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testBuilderCreatesStationCode() {

        // arrange
        StationCodeBuilder builder = new StationCodeBuilder().setName("123").setStationId("abc");

        // act
        StationCode stationCode = builder.build();

        // assert
        assertThat(stationCode, instanceOf(StationCode.class));
        assertThat(stationCode.getName(), equalTo("123"));
        assertThat(stationCode.getStationId().get(), equalTo("abc"));
    }
}
