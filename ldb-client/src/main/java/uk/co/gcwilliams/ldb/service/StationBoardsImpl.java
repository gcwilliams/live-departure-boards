package uk.co.gcwilliams.ldb.service;

import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.ServiceDetail;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.model.builder.ServiceDetailBuilder;
import uk.co.gcwilliams.ldb.model.builder.StationBoardBuilder;
import uk.co.gcwilliams.ldb.request.HttpClient;

/**
 * The station boards implementation
 *
 * @author Gareth Williams
 */
public class StationBoardsImpl extends AbstractService implements StationBoards {

    private static final String TO = "to";

    private static final String FROM = "from";

    private static final String SERVICE_ID = "serviceId";

    private static final String DEPARTURES_BOARD_PATH = "/api/boards/departures/%s";

    private static final String ARRIVALS_BOARD_PATH = "/api/boards/arrivals/%s";

    private static final String SERVICE_DETAIL_PATH = "/api/boards/detail/%s";

    /**
     * Default constructor
     *
     */
    public StationBoardsImpl(HttpClient client) {
        super(client);
    }

    @Override
    public StationBoard getArrivalBoard(StationCode to) {
        String path = String.format(ARRIVALS_BOARD_PATH, to.getStationId().get());
        return client.<StationBoard>request().withPath(path).execute(buildWith(StationBoardBuilder.class));
    }

    @Override
    public StationBoard getArrivalBoard(StationCode to, StationCode from) {
        String path = String.format(ARRIVALS_BOARD_PATH, to.getStationId().get());
        return client.<StationBoard>request()
            .withPath(path)
            .withParam(FROM, from.getStationId().get())
            .execute(buildWith(StationBoardBuilder.class));
    }

    @Override
    public StationBoard getDepartureBoard(StationCode from) {
        String path = String.format(DEPARTURES_BOARD_PATH, from.getStationId().get());
        return client.<StationBoard>request().withPath(path).execute(buildWith(StationBoardBuilder.class));
    }

    @Override
    public StationBoard getDepartureBoard(StationCode from, StationCode to) {
        String path = String.format(DEPARTURES_BOARD_PATH, from.getStationId().get());
        return client.<StationBoard>request()
            .withPath(path)
            .withParam(TO, to.getStationId().get())
            .execute(buildWith(StationBoardBuilder.class));
    }

    @Override
    public ServiceDetail getServiceDetail(Id<Service> id) {
        String path = String.format(SERVICE_DETAIL_PATH, id);
        return client.<ServiceDetail>request()
            .withPath(path)
            .withParam(SERVICE_ID, id.get())
            .execute(buildWith(ServiceDetailBuilder.class));
    }
}
