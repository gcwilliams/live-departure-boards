package uk.co.gcwilliams.ldb.service;

import com.google.common.base.Function;
import com.google.common.io.CharStreams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.ServiceDetail;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.model.builder.StationCodeBuilder;
import uk.co.gcwilliams.ldb.request.HttpClient;
import uk.co.gcwilliams.ldb.request.RequestBuilder;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * The station board implementation test
 *
 * @author Gareth Williams
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class StationBoardImplTest {

    @Mock
    private HttpClient client;

    @Mock
    private RequestBuilder builder;

    @Test
    public void testGetArrivalBoard() throws IOException {

        // arrange
        final String mockResponse = getMockResponse("tbd-arrival-response.json");
        StationCode threeBridges = new StationCodeBuilder().setName("Three Bridges").setStationId("TBD").build();
        when(client.<StationBoard>request()).thenReturn(builder);
        when(builder.withPath(anyString())).thenReturn(builder);
        when(builder.withParam(anyString(), anyString())).thenReturn(builder);
        when(builder.execute(any(Function.class))).thenAnswer(new Answer<StationBoard>() {
            @Override
            public StationBoard answer(InvocationOnMock invocation) throws Throwable {
                Function<String, StationBoard> fn = (Function<String, StationBoard>)invocation.getArguments()[0];
                return fn.apply(mockResponse);
            }
        });

        // act
        StationBoard board = new StationBoardsImpl(client).getArrivalBoard(threeBridges);

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
        final String mockResponse = getMockResponse("tbd-bug-arrival-response.json");
        StationCode threeBridges = new StationCodeBuilder().setName("Three Bridges").setStationId("TBD").build();
        StationCode burgessHill = new StationCodeBuilder().setName("Burgess Hill").setStationId("BUG").build();
        when(client.<StationBoard>request()).thenReturn(builder);
        when(builder.withPath(anyString())).thenReturn(builder);
        when(builder.withParam(anyString(), anyString())).thenReturn(builder);
        when(builder.execute(any(Function.class))).thenAnswer(new Answer<StationBoard>() {
            @Override
            public StationBoard answer(InvocationOnMock invocation) throws Throwable {
                Function<String, StationBoard> fn = (Function<String, StationBoard>) invocation.getArguments()[0];
                return fn.apply(mockResponse);
            }
        });

        // act
        StationBoard board = new StationBoardsImpl(client).getArrivalBoard(threeBridges, burgessHill);

        // assert
        assertThat(board, notNullValue());
    }

    @Test
    public void testGetDepartureBoard() throws IOException {

        // arrange
        final String mockResponse = getMockResponse("tbd-departure-response.json");
        StationCode threeBridges = new StationCodeBuilder().setName("Three Bridges").setStationId("TBD").build();
        when(client.<StationBoard>request()).thenReturn(builder);
        when(builder.withPath(anyString())).thenReturn(builder);
        when(builder.withParam(anyString(), anyString())).thenReturn(builder);
        when(builder.execute(any(Function.class))).thenAnswer(new Answer<StationBoard>() {
            @Override
            public StationBoard answer(InvocationOnMock invocation) throws Throwable {
                Function<String, StationBoard> fn = (Function<String, StationBoard>) invocation.getArguments()[0];
                return fn.apply(mockResponse);
            }
        });

        // act
        StationBoard board = new StationBoardsImpl(client).getDepartureBoard(threeBridges);

        // assert
        assertThat(board, notNullValue());
    }

    @Test
    public void testGetDepartureBoardWithFrom() throws IOException {

        // arrange
        final String mockResponse = getMockResponse("tbd-bug-departure-response.json");
        StationCode threeBridges = new StationCodeBuilder().setName("Three Bridges").setStationId("TBD").build();
        StationCode burgessHill = new StationCodeBuilder().setName("Burgess Hill").setStationId("BUG").build();
        when(client.<StationBoard>request()).thenReturn(builder);
        when(builder.withPath(anyString())).thenReturn(builder);
        when(builder.withParam(anyString(), anyString())).thenReturn(builder);
        when(builder.execute(any(Function.class))).thenAnswer(new Answer<StationBoard>() {
            @Override
            public StationBoard answer(InvocationOnMock invocation) throws Throwable {
                Function<String, StationBoard> fn = (Function<String, StationBoard>) invocation.getArguments()[0];
                return fn.apply(mockResponse);
            }
        });

        // act
        StationBoard board = new StationBoardsImpl(client).getDepartureBoard(threeBridges, burgessHill);

        // assert
        assertThat(board, notNullValue());
    }

    @Test
    public void testGetServiceDetail() throws IOException {

        // arrange
        final String mockResponse = getMockResponse("service-detail-response.json");
        when(client.<StationBoard>request()).thenReturn(builder);
        when(builder.withPath(anyString())).thenReturn(builder);
        when(builder.withParam(anyString(), anyString())).thenReturn(builder);
        when(builder.execute(any(Function.class))).thenAnswer(new Answer<ServiceDetail>() {
            @Override
            public ServiceDetail answer(InvocationOnMock invocation) throws Throwable {
                Function<String, ServiceDetail> fn = (Function<String, ServiceDetail>) invocation.getArguments()[0];
                return fn.apply(mockResponse);
            }
        });

        // act
        ServiceDetail detail = new StationBoardsImpl(client).getServiceDetail(new Id<Service>(""));

        // assert
        assertThat(detail, notNullValue());
    }

    private String getMockResponse(String name) throws IOException {
        InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(name));
        return CharStreams.toString(reader);
    }
}
