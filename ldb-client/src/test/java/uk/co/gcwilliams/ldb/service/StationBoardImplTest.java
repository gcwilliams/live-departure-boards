package uk.co.gcwilliams.ldb.service;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.net.MediaType;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.ServiceDetail;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.model.builder.StationCodeBuilder;
import uk.co.gcwilliams.ldb.service.StationBoardsImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * The station board implementation test
 *
 * @author Gareth Williams
 */
@RunWith(MockitoJUnitRunner.class)
public class StationBoardImplTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse response;

    @Mock
    private HttpEntity entity;

    @Mock
    private Header contentType;

    @Before
    public void setup() throws IOException {
        when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(response);
        when(response.getEntity()).thenReturn(entity);
        when(entity.getContentType()).thenReturn(contentType);
        when(contentType.getValue()).thenReturn(MediaType.JSON_UTF_8.toString());
    }

    @Test
    public void testGetArrivalBoard() throws IOException {

        // arrange
        String mockResponse = getMockResponse("tbd-arrival-response.json");
        StationCode threeBridges = new StationCodeBuilder().setName("Three Bridges").setStationId("TBD").build();
        when(entity.getContent()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes(Charsets.UTF_8)));

        // act
        StationBoard board = new StationBoardsImpl(httpClient, "host", 443, "").getArrivalBoard(threeBridges);

        // assert
        assertThat(board, notNullValue());
        assertThat(board.getServices().size(), equalTo(1));
        assertThat(board.getServices().get(0).getEstimatedTimeOfArrival(), notNullValue());
        assertThat(board.getServices().get(0).getEstimatedTimeOfArrival().isPresent(), equalTo(true));
        assertThat(board.getServices().get(0).getEstimatedTimeOfDeparture(), notNullValue());
        assertThat(board.getServices().get(0).getEstimatedTimeOfDeparture().isPresent(), equalTo(false));
    }

    @Test
    public void testGetArrivalBoardWithTo() throws IOException {

        // arrange
        String mockResponse = getMockResponse("tbd-bug-arrival-response.json");
        StationCode threeBridges = new StationCodeBuilder().setName("Three Bridges").setStationId("TBD").build();
        StationCode burgessHill = new StationCodeBuilder().setName("Burgess Hill").setStationId("BUG").build();
        when(entity.getContent()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes(Charsets.UTF_8)));

        // act
        StationBoard board = new StationBoardsImpl(httpClient, "host", 443, "").getArrivalBoard(threeBridges, burgessHill);

        // assert
        assertThat(board, notNullValue());
    }

    @Test
    public void testGetDepartureBoard() throws IOException {

        // arrange
        String mockResponse = getMockResponse("tbd-departure-response.json");
        StationCode threeBridges = new StationCodeBuilder().setName("Three Bridges").setStationId("TBD").build();
        when(entity.getContent()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes(Charsets.UTF_8)));

        // act
        StationBoard board = new StationBoardsImpl(httpClient, "host", 443, "").getDepartureBoard(threeBridges);

        // assert
        assertThat(board, notNullValue());
    }

    @Test
    public void testGetDepartureBoardWithFrom() throws IOException {

        // arrange
        String mockResponse = getMockResponse("tbd-bug-departure-response.json");
        StationCode threeBridges = new StationCodeBuilder().setName("Three Bridges").setStationId("TBD").build();
        StationCode burgessHill = new StationCodeBuilder().setName("Burgess Hill").setStationId("BUG").build();
        when(entity.getContent()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes(Charsets.UTF_8)));

        // act
        StationBoard board = new StationBoardsImpl(httpClient, "host", 443, "").getDepartureBoard(threeBridges, burgessHill);

        // assert
        assertThat(board, notNullValue());
    }

    @Test
    public void testGetServiceDetail() throws IOException {

        // arrange
        String mockResponse = getMockResponse("service-detail-response.json");
        when(entity.getContent()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes(Charsets.UTF_8)));

        // act
        ServiceDetail detail = new StationBoardsImpl(httpClient, "host", 443, "").getServiceDetail(new Id<Service>(""));

        // assert
        assertThat(detail, notNullValue());
    }

    private String getMockResponse(String name) throws IOException {
        InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(name));
        return CharStreams.toString(reader);
    }
}
