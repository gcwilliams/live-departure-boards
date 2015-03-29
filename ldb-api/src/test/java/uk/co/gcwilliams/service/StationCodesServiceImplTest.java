package uk.co.gcwilliams.service;

import org.apache.lucene.index.DirectoryReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.StationCode;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * The station code service implementation tests
 *
 * Created by GWilliams on 25/03/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class StationCodesServiceImplTest {

    @Mock
    private StationCode stationCode;

    @Mock
    private DirectoryReader reader;

    @Test
    public void getUnknownStationCode() {

        // arrange
        when(stationCode.getStationId()).thenReturn(new Id<>("123"));
        StationCodesService service = new StationCodesServiceImpl(singletonList(stationCode), reader);

        // act
        Optional<StationCode> code = service.getCode("Homer Simpson");

        // assert
        assertThat(code, notNullValue());
        assertThat(code.isPresent(), equalTo(false));
    }

    @Test
    public void getStationCode() {

        // arrange
        when(stationCode.getStationId()).thenReturn(new Id<>("TBD"));
        StationCodesService service = new StationCodesServiceImpl(singletonList(stationCode), reader);

        // act
        Optional<StationCode> code = service.getCode("TBD");

        // assert
        assertThat(code, notNullValue());
        assertThat(code.isPresent(), equalTo(true));
        assertThat(code.get(), equalTo(stationCode));
    }
}
