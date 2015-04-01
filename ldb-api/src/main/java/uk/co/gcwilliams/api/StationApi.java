package uk.co.gcwilliams.api;

import com.google.common.base.Strings;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.logging.Loggable;
import uk.co.gcwilliams.service.StationCodesService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * The station API
 *
 * @author Gareth Williams
 */
@RolesAllowed("ldb-api") @Path("/stations")
public class StationApi {

    private final StationCodesService stationCodesService;

    /**
     * Default constructor
     *
     * @param stationCodesService the station codes service
     */
    @Inject
    public StationApi(StationCodesService stationCodesService) {
        this.stationCodesService = stationCodesService;
    }

    /**
     * Finds the station codes based on the specified term
     *
     * @param term the search term
     * @return the suggested codes
     */
    @GET
    @Loggable
    public List<StationCode> find(@QueryParam("name") String term) {
        if (Strings.isNullOrEmpty(term)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return stationCodesService.find(term);
    }
}