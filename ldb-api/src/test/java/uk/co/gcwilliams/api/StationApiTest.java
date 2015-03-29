package uk.co.gcwilliams.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.gcwilliams.ldb.model.builder.StationCodeBuilder;
import uk.co.gcwilliams.service.StationCodesService;
import uk.co.gcwilliams.service.StationCodesServiceImpl;
import uk.co.gcwilliams.ldb.model.StationCode;

import javax.ws.rs.WebApplicationException;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * The station API tests
 *
 * @author Gareth Williams
 */
@RunWith(MockitoJUnitRunner.class)
public class StationApiTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private StationCodesService stationCodesService;

    private StationApi api;

    @Before
    public void setup() {
        api = new StationApi(stationCodesService);
    }

    @Test
    public void stationApiWithNullValue() {

        // arrange
        exception.expect(WebApplicationException.class);

        // act
        api.find(null);
    }

    @Test
    public void stationApiWithEmptyValue() {

        // arrange
        exception.expect(WebApplicationException.class);

        // act
        api.find("");
    }

    @Test
    public void stationApiCallsService() {

        // arrange
        List<StationCode> codes = singletonList(new StationCodeBuilder()
            .setName("Three Bridges")
            .setStationId("TBD")
            .build()
        );
        when(stationCodesService.find(eq("Three Bridges"))).thenReturn(codes);
        StationApi api = new StationApi(stationCodesService);

        // act
        List<StationCode> found = api.find("Three Bridges");

        // assert
        Assert.assertThat(found.size(), equalTo(1));
        Assert.assertThat(found.get(0).getName(), equalTo("Three Bridges"));
    }
}
