package uk.co.gcwilliams.api;

import com.google.common.base.Strings;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.ServiceDetail;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.service.StationBoards;
import uk.co.gcwilliams.service.StationCodesService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * The boards API
 *
 * @author Gareth Williams (466567)
 */
@RolesAllowed("ldb-api")
@Path("/boards")
public class BoardsApi {

    private final StationCodesService stationCodesService;

    private final StationBoards boards;

    /**
     * Default constructor
     *
     * @param stationCodesService The station codes service
     * @param boards The station boards
     */
    @Inject
    public BoardsApi(StationCodesService stationCodesService, StationBoards boards) {
        this.stationCodesService = stationCodesService;
        this.boards = boards;
    }

    /**
     * Gets the departures board
     *
     * @param from the from station code
     * @param to the to station code
     * @return the station board
     */
    @GET
    @Path("/departures/{from}")
    public StationBoard getDeparturesBoard(@PathParam("from") String from, @QueryParam("to") String to) {

        Optional<StationCode> code = stationCodesService.getCode(from);

        if (!code.isPresent()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (Strings.isNullOrEmpty(to)) {
            return boards.getDepartureBoard(code.get());
        }

        Optional<StationCode> toCode = stationCodesService.getCode(to);
        if (!toCode.isPresent()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return boards.getDepartureBoard(code.get(), toCode.get());
    }

    /**
     * Gets the arrivals board
     *
     * @param to the to station code
     * @param from the from station code
     * @return the station board
     */
    @GET
    @Path("/arrivals/{to}")
    public StationBoard getArrivalsBoard(@PathParam("to") String to, @QueryParam("from") String from) {

        Optional<StationCode> code = stationCodesService.getCode(to);

        if (!code.isPresent()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (Strings.isNullOrEmpty(from)) {
            return boards.getArrivalBoard(code.get());
        }

        Optional<StationCode> fromCode = stationCodesService.getCode(from);
        if (!fromCode.isPresent()) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return boards.getArrivalBoard(code.get(), fromCode.get());
    }

    /**
     * Gets the service detail
     *
     * @param serviceId the service ID
     * @return the service detail
     */
    @GET
    @Path("/detail/{serviceId:.+}")
    public ServiceDetail getServiceDetail(@PathParam("serviceId") String serviceId) {
        return boards.getServiceDetail(new Id<>(serviceId));
    }
}
