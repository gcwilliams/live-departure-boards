package uk.co.gcwilliams.ldb.service.impl;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.ServiceDetail;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.model.builder.ServiceDetailBuilder;
import uk.co.gcwilliams.ldb.model.builder.StationBoardBuilder;
import uk.co.gcwilliams.ldb.service.StationBoards;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

/**
 * The station boards implementation
 *
 * @author Gareth Williams (466567)
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
     * @param httpClient The http client
     * @param host The host
     * @param authorizationHeader The full authorization header, base64 encoded
     */
    public StationBoardsImpl(HttpClient httpClient, String host, String authorizationHeader) {
        this(httpClient, host, -1, authorizationHeader);
    }

    /**
     * Constructor specifying a port
     *
     * @param httpClient The http client
     * @param host The host
     * @param port The port
     * @param authorizationHeader The full authorization header, base64 encoded
     */
    public StationBoardsImpl(HttpClient httpClient, String host, int port, String authorizationHeader) {
        super(httpClient, host, port, authorizationHeader);
    }

    @Override
    public StationBoard getArrivalBoard(StationCode to) {
        String path = String.format(ARRIVALS_BOARD_PATH, to.getStationId().get());
        String content = executeRequest(createGetRequest(createURI(path)));
        return GSON.fromJson(content, StationBoardBuilder.class).build();
    }

    @Override
    public StationBoard getArrivalBoard(StationCode to, StationCode from) {
        String path = String.format(ARRIVALS_BOARD_PATH, to.getStationId().get());
        String content = executeRequest(createGetRequest(createURI(path, Param.create(FROM, from.getStationId().get()))));
        return GSON.fromJson(content, StationBoardBuilder.class).build();
    }

    @Override
    public StationBoard getDepartureBoard(StationCode from) {
        String path = String.format(DEPARTURES_BOARD_PATH, from.getStationId().get());
        String content = executeRequest(createGetRequest(createURI(path)));
        return GSON.fromJson(content, StationBoardBuilder.class).build();
    }

    @Override
    public StationBoard getDepartureBoard(StationCode from, StationCode to) {
        String path = String.format(DEPARTURES_BOARD_PATH, from.getStationId().get());
        String content = executeRequest(createGetRequest(createURI(path, Param.create(TO, to.getStationId().get()))));
        return GSON.fromJson(content, StationBoardBuilder.class).build();
    }

    @Override
    public ServiceDetail getServiceDetail(Id<Service> id) {
        String path = String.format(SERVICE_DETAIL_PATH, id);
        String content = executeRequest(createGetRequest(createURI(path, Param.create(SERVICE_ID, id.get()))));
        return GSON.fromJson(content, ServiceDetailBuilder.class).build();
    }
}
