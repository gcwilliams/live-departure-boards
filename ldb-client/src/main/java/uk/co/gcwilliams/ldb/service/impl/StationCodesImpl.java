package uk.co.gcwilliams.ldb.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.apache.http.client.HttpClient;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.model.builder.BuilderTransformation;
import uk.co.gcwilliams.ldb.model.builder.StationCodeBuilder;
import uk.co.gcwilliams.ldb.service.StationCodes;

import java.lang.reflect.Type;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

/**
 * The station codes implementation
 *
 * @author Gareth Williams
 */
public class StationCodesImpl extends AbstractService implements StationCodes {

    private static final String SUGGEST_PATH = "/api/stations";

    private static final String NAME = "name";

    private static final Type STATION_CODE_LIST_TYPE = new TypeToken<List<StationCodeBuilder>>() {}.getType();

    /**
     * Default constructor
     *
     * @param httpClient The http client
     * @param host The host
     * @param authorizationHeader The full authorization header, base64 encoded
     */
    public StationCodesImpl(HttpClient httpClient, String host, String authorizationHeader) {
        this(httpClient, host, -1, authorizationHeader);
    }

    /**
     * Default constructor
     *
     * @param httpClient The http client
     * @param host The host
     * @param port The port
     * @param authorizationHeader The full authorization header, base64 encoded
     */
    public StationCodesImpl(HttpClient httpClient, String host, int port, String authorizationHeader) {
        super(httpClient, host, port, authorizationHeader);
    }

    @Override
    public List<StationCode> suggestStationCodes(String term) {
        String content = executeRequest(createGetRequest(createURI(SUGGEST_PATH, Param.create(NAME, term))));
        List<StationCodeBuilder> codes = GSON.fromJson(content, STATION_CODE_LIST_TYPE);
        return transform(codes, new BuilderTransformation<StationCode>());
    }
}
