package uk.co.gcwilliams.ldb.model.builder;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.gcwilliams.ldb.model.Station;
import uk.co.gcwilliams.ldb.model.StationBoard;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * The station board builder tests
 *
 * Created by GWilliams on 22/03/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class StationBoardBuilderTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private StationBuilder stationBuilder;

    @Mock
    private Station station;

    @Test
    public void testValidationStationIsNull() {

        // arrange
        StationBoardBuilder builder = new StationBoardBuilder()
            .setGeneratedAt(DateTime.now())
            .setServices(Lists.<ServiceBuilder>newArrayList());

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationServicesAreNull() {

        // arrange
        StationBoardBuilder builder = new StationBoardBuilder()
            .setGeneratedAt(DateTime.now())
            .setStation(stationBuilder);

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testValidationGeneratedAtIsNull() {

        // arrange
        StationBoardBuilder builder = new StationBoardBuilder()
            .setServices(Lists.<ServiceBuilder>newArrayList())
            .setStation(stationBuilder);

        // act
        exception.expect(IllegalStateException.class);
        builder.build();
    }

    @Test
    public void testBuilderCreatesStationBoard() {

        // arrange
        when(stationBuilder.build()).thenReturn(station);
        StationBoardBuilder builder = new StationBoardBuilder()
            .setGeneratedAt(DateTime.now())
            .setServices(Lists.<ServiceBuilder>newArrayList())
            .setStation(stationBuilder);

        // act
        StationBoard board = builder.build();

        // assert
        assertThat(board, notNullValue());
        assertThat(board.getStation(), instanceOf(Station.class));
        assertThat(board.getGeneratedAt(), instanceOf(DateTime.class));
        assertThat(board.getServices(), instanceOf(List.class));
        assertThat(board.getServices().size(), equalTo(0));
    }
}