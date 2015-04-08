package uk.co.gcwilliams.ldb.service;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joda.time.DateTime;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.Station;
import uk.co.gcwilliams.ldb.model.builder.Builder;
import uk.co.gcwilliams.ldb.request.HttpClient;
import uk.co.gcwilliams.ldb.serializers.GSonDateTimeDeserializer;
import uk.co.gcwilliams.ldb.serializers.GSonIdDeserializer;
import uk.co.gcwilliams.ldb.serializers.GSonOptionalDeserializer;

import java.lang.reflect.Type;

/**
 * The abstract service
 *
 * @author Gareth Williams
 */
public class AbstractService {

    private static final Type DATE_TIME_TYPE = new TypeToken<DateTime>(){}.getType();

    private static final Type OPTIONAL_TYPE = new TypeToken<Optional>(){}.getType();

    private static final Type SERVICE_ID_TYPE = new TypeToken<Id<Service>>(){}.getType();

    private static final Type STATION_ID_TYPE = new TypeToken<Id<Station>>(){}.getType();

    protected static final Gson GSON;

    protected final HttpClient client;

    static {
        GSON = new GsonBuilder()
            .registerTypeAdapter(DATE_TIME_TYPE, new GSonDateTimeDeserializer())
            .registerTypeAdapter(OPTIONAL_TYPE, new GSonOptionalDeserializer())
            .registerTypeAdapter(SERVICE_ID_TYPE, new GSonIdDeserializer<Service>())
            .registerTypeAdapter(STATION_ID_TYPE, new GSonIdDeserializer<Station>())
            .create();
    }

    /**
     * Default constructor
     *
     * @param client the HTTP client
     */
    protected AbstractService(HttpClient client) {
        this.client = client;
    }

    /**
     * Creates a function to build the required type
     *
     * @param clazz the builder clazz
     * @return the require type
     */
    protected <T> Function<String, T> buildWith(final Class<? extends Builder<T>> clazz) {
        return new Function<String, T>() {
            @Override
            public T apply(String response) {
                return GSON.fromJson(response, clazz).build();
            }
        };
    }
}