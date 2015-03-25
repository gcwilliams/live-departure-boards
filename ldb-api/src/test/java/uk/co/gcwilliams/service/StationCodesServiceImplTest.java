package uk.co.gcwilliams.service;

import org.junit.Test;
import uk.co.gcwilliams.ldb.model.StationCode;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * The station code service implementation tests
 *
 * Created by GWilliams on 25/03/2015.
 */
public class StationCodesServiceImplTest {

    private StationCodesService service = new StationCodesServiceImpl();

    @Test
    public void getUnknownStationCode() {

        // act
        Optional<StationCode> code = service.getCode("Homer Simpson");

        // assert
        assertThat(code, notNullValue());
        assertThat(code.isPresent(), equalTo(false));
    }

    @Test
    public void getStationCode() {

        // act
        Optional<StationCode> code = service.getCode("TBD");

        // assert
        assertThat(code, notNullValue());
        assertThat(code.isPresent(), equalTo(true));
    }

    @Test
    public void findStationCodes() {

        // act
        List<StationCode> codes = service.find("Three Bridge");

        // assert
        assertThat(codes, notNullValue());
        assertThat(codes.size(), equalTo(10));
        assertThat(codes.get(0).getName(), equalTo("Three Bridges"));
    }
}
