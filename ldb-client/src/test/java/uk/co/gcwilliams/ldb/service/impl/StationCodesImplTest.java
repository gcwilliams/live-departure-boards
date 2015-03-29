package uk.co.gcwilliams.ldb.service.impl;

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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.gcwilliams.ldb.model.StationCode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The station codes tests
 *
 * @author Gareth Williams
 */
@RunWith(MockitoJUnitRunner.class)
public class StationCodesImplTest {

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
    public void testInvalidParameter() throws IOException {

        // arrange
        String mockResponse = getMockResponse("suggestions.json");
        when(entity.getContent()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes(Charsets.UTF_8)));

        // act
        new StationCodesImpl(httpClient, "host", 443, "").suggestStationCodes("");

        // assert
        ArgumentCaptor<HttpUriRequest> captor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClient).execute(captor.capture());
        assertThat(captor.getValue().getURI().getQuery(), equalTo(""));
    }

    @Test
    public void testGetSuggestion() throws IOException {

        // arrange
        String mockResponse = getMockResponse("suggestions.json");
        when(entity.getContent()).thenReturn(new ByteArrayInputStream(mockResponse.getBytes(Charsets.UTF_8)));

        // act
        List<StationCode> codes = new StationCodesImpl(httpClient, "host", 443, "").suggestStationCodes("Three B");

        // assert
        assertThat(codes, notNullValue());
    }

    private String getMockResponse(String name) throws IOException {
        InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(name));
        return CharStreams.toString(reader);
    }
}
