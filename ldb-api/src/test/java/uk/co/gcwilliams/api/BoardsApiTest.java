package uk.co.gcwilliams.api;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.gcwilliams.service.StationCodesService;
import uk.co.gcwilliams.service.StationCodesServiceImpl;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.ServiceDetail;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.model.builder.StationCodeBuilder;
import uk.co.gcwilliams.ldb.service.StationBoards;

import javax.ws.rs.WebApplicationException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The boards API tests
 *
 * Created by GWilliams on 22/03/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class BoardsApiTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private StationCodesService stationCodes;

    @Mock
    private StationBoards stationBoards;

    @Mock
    private StationBoard mockBoard;

    @Mock
    private ServiceDetail serviceDetail;

    private BoardsApi api;

    @Before
    public void setup() {
        api = new BoardsApi(stationCodes, stationBoards);
    }

    @Test
    public void testAllArrivalsStationCodeNotFound() {

        // arrange
        when(stationCodes.getCode(eq("BTN"))).thenReturn(Optional.empty());

        // act
        exception.expect(WebApplicationException.class);
        api.getDeparturesBoard("BTN", null);
    }

    @Test
    public void testAllArrivalsStation() {

        // arrange
        StationCode btn = new StationCodeBuilder().setName("Brighton").setStationId("BTN").build();
        when(stationCodes.getCode(eq("BTN"))).thenReturn(Optional.of(btn));
        when(stationBoards.getArrivalBoard(eq(btn))).thenReturn(mockBoard);

        // act
        StationBoard board = api.getArrivalsBoard("BTN", null);

        // assert
        assertThat(board, notNullValue());
        ArgumentCaptor<StationCode> btnCaptor = ArgumentCaptor.forClass(StationCode.class);
        verify(stationBoards).getArrivalBoard(btnCaptor.capture());
        assertThat(btnCaptor.getValue(), notNullValue());
        assertThat(btnCaptor.getValue().getStationId().get(), equalTo("BTN"));
    }

    @Test
    public void testAllDeparturesStationCodeNotFound() {

        // arrange
        StationCode btn = new StationCodeBuilder().setName("Brighton").setStationId("BTN").build();
        when(stationCodes.getCode(eq("BTN"))).thenReturn(Optional.of(btn));
        when(stationCodes.getCode(eq("TBD"))).thenReturn(Optional.empty());

        // act
        exception.expect(WebApplicationException.class);
        api.getDeparturesBoard("BTN", "TBD");
    }

    @Test
    public void testAllDeparturesStation() {

        // arrange
        StationCode btn = new StationCodeBuilder().setName("Brighton").setStationId("BTN").build();
        StationCode tbd = new StationCodeBuilder().setName("Three Bridges").setStationId("TBD").build();
        when(stationCodes.getCode(eq("BTN"))).thenReturn(Optional.of(btn));
        when(stationCodes.getCode(eq("TBD"))).thenReturn(Optional.of(tbd));
        when(stationBoards.getArrivalBoard(eq(btn), eq(tbd))).thenReturn(mockBoard);

        // act
        StationBoard board = api.getArrivalsBoard("BTN", "TBD");

        // assert
        assertThat(board, notNullValue());
        ArgumentCaptor<StationCode> btnCaptor = ArgumentCaptor.forClass(StationCode.class);
        ArgumentCaptor<StationCode> tbdCaptor = ArgumentCaptor.forClass(StationCode.class);
        verify(stationBoards).getArrivalBoard(btnCaptor.capture(), tbdCaptor.capture());
        assertThat(btnCaptor.getValue(), notNullValue());
        assertThat(btnCaptor.getValue().getStationId().get(), equalTo("BTN"));
        assertThat(tbdCaptor.getValue(), notNullValue());
        assertThat(tbdCaptor.getValue().getStationId().get(), equalTo("TBD"));
    }

    @Test
    public void testGetServiceDetail() {

        // arrange
        Id<Service> serviceId = new Id<>("123");
        when(stationBoards.getServiceDetail(eq(serviceId))).thenReturn(serviceDetail);

        // act
        ServiceDetail detail = api.getServiceDetail("123");

        // assert
        assertThat(detail, notNullValue());
    }
}
