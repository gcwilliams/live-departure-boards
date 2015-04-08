package uk.co.gcwilliams.ldb.service;

import com.google.common.base.Function;
import com.google.common.io.CharStreams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.request.HttpClient;
import uk.co.gcwilliams.ldb.request.RequestBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The station codes tests
 *
 * @author Gareth Williams
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class StationCodesImplTest {

    @Mock
    private HttpClient client;

    @Mock
    private RequestBuilder builder;

    @Test
    public void testInvalidParameter() throws IOException {

        // arrange
        final String mockResponse = getMockResponse("suggestions.json");
        when(client.<StationBoard>request()).thenReturn(builder);
        when(builder.withPath(anyString())).thenReturn(builder);
        when(builder.withParam(anyString(), anyString())).thenReturn(builder);
        when(builder.execute(any(Function.class))).thenAnswer(new Answer<List<StationCode>>() {
            @Override
            public List<StationCode> answer(InvocationOnMock invocation) throws Throwable {
                Function<String, List<StationCode>> fn = (Function<String, List<StationCode>>) invocation.getArguments()[0];
                return fn.apply(mockResponse);
            }
        });

        // act
        new StationCodesImpl(client).suggestStationCodes("");

        // assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(builder).withParam(eq("name"), captor.capture());
        assertThat(captor.getValue(), equalTo(""));
    }

    @Test
    public void testGetSuggestion() throws IOException {

        // arrange
        final String mockResponse = getMockResponse("suggestions.json");
        when(client.<StationBoard>request()).thenReturn(builder);
        when(builder.withPath(anyString())).thenReturn(builder);
        when(builder.withParam(anyString(), anyString())).thenReturn(builder);
        when(builder.execute(any(Function.class))).thenAnswer(new Answer<List<StationCode>>() {
            @Override
            public List<StationCode> answer(InvocationOnMock invocation) throws Throwable {
                Function<String, List<StationCode>> fn = (Function<String, List<StationCode>>) invocation.getArguments()[0];
                return fn.apply(mockResponse);
            }
        });

        // act
        List<StationCode> codes = new StationCodesImpl(client).suggestStationCodes("Three B");

        // assert
        assertThat(codes, notNullValue());
    }

    private String getMockResponse(String name) throws IOException {
        InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(name));
        return CharStreams.toString(reader);
    }
}
