package uk.co.gcwilliams.api;

import org.junit.Assert;
import org.junit.Test;
import uk.co.gcwilliams.codes.StationCodes;
import uk.co.gcwilliams.ldb.model.StationCode;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * The station API tests
 *
 * @author Gareth Williams (466567)
 */
public class StationApiTest {

    private final StationCodes codes = new StationCodes();

    @Test
    public void testTenStationsFound() {

        // arrange
        StationApi api = new StationApi(codes);

        // act
        List<StationCode> found = api.find("Three Bridges");

        // assert
        Assert.assertThat(found.size(), equalTo(2));
        Assert.assertThat(found.get(0).getName(), equalTo("Three Bridges"));
    }
}
