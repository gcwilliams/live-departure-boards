package uk.co.gcwilliams.service;

import org.glassfish.jersey.spi.Contract;
import uk.co.gcwilliams.ldb.model.StationCode;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Optional;

/**
 * The station code service
 *
 * Created by GWilliams on 25/03/2015.
 */
@Contract
public interface StationCodesService {

    /**
     * Find the station with the specified code
     *
     * @param code The code
     * @return The station
     */
    Optional<StationCode> getCode(String code);

    /**
     * Finds the station codes
     *
     * @param term the term to search with
     * @return The station codes
     */
    List<StationCode> find(@QueryParam("name") String term);
}
