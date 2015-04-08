package uk.co.gcwilliams.ldb.service;

import com.google.common.base.Function;
import com.google.common.reflect.TypeToken;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.model.builder.BuilderTransformation;
import uk.co.gcwilliams.ldb.model.builder.StationBoardBuilder;
import uk.co.gcwilliams.ldb.model.builder.StationCodeBuilder;
import uk.co.gcwilliams.ldb.request.HttpClient;
import uk.co.gcwilliams.ldb.request.RequestBuilderImpl;

import java.lang.reflect.Type;
import java.util.List;

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
     * @param client The HTTP client
     */
    public StationCodesImpl(HttpClient client) {
        super(client);
    }

    @Override
    public List<StationCode> suggestStationCodes(String term) {
        return client.<List<StationCode>>request()
            .withPath(SUGGEST_PATH)
            .withParam(NAME, term)
            .execute(STATION_CODE_BUILDER);
    }

    /**
     * The station code builder
     *
     */
    private static final Function<String, List<StationCode>> STATION_CODE_BUILDER = new Function<String, List<StationCode>>() {
        @Override
        public List<StationCode> apply(String body) {
            List<StationCodeBuilder> codes = GSON.fromJson(body, STATION_CODE_LIST_TYPE);
            return transform(codes, new BuilderTransformation<StationCode>());
        }
    };
}
