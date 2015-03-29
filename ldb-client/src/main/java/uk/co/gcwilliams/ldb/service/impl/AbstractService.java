package uk.co.gcwilliams.ldb.service.impl;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.joda.time.DateTime;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.Station;
import uk.co.gcwilliams.ldb.serializers.GSonDateTimeDeserializer;
import uk.co.gcwilliams.ldb.serializers.GSonIdDeserializer;
import uk.co.gcwilliams.ldb.serializers.GSonOptionalDeserializer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static java.util.Arrays.asList;

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

    private static final Joiner PARAMETER_JOINER = Joiner.on("&");

    protected static final Gson GSON;

    private final HttpClient httpClient;

    private final String host;

    private final int port;

    private final String authorizationHeader;

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
     * @param httpClient The http client
     * @param host The host
     * @param port The port
     * @param authorizationHeader The full authorization header, base64 encoded
     */
    protected AbstractService(HttpClient httpClient, String host, int port, String authorizationHeader) {
        this.httpClient = httpClient;
        this.host = host;
        this.port = port;
        this.authorizationHeader = authorizationHeader;
    }

    /**
     * Creates the GET request
     *
     * @param uri The URI
     * @return The get request
     */
    protected HttpGet createGetRequest(URI uri) {
        HttpGet get = new HttpGet(uri);
        get.setHeader(HttpHeaders.ACCEPT, MediaType.JSON_UTF_8.toString());
        get.setHeader(HttpHeaders.AUTHORIZATION, authorizationHeader);
        return get;
    }

    /**
     * Creates the URI
     *
     * @param path The path
     * @return The URI builder
     */
    protected URI createURI(String path, Param... params) {
        Iterable<String> parameters = transform(filter(asList(params), IS_VALID_PARAM_PREDICATE), TO_QUERY_PARAM_FUNCTION);
        String combinedParameters = PARAMETER_JOINER.join(parameters);
        try {
            return new URI("https", null, host, port, path, combinedParameters, null);
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Executes the request, and converts the content of the response into a string
     *
     * @param request The request
     * @return The content of the response as a string
     */
    protected String executeRequest(HttpUriRequest request) {
        try {
            HttpResponse response = httpClient.execute(request);
            MediaType mediaType = MediaType.parse(response.getEntity().getContentType().getValue());
            return CharStreams.toString(new InputStreamReader(
                    response.getEntity().getContent(),
                    mediaType.charset().isPresent() ? mediaType.charset().get() : Charsets.UTF_8
            ));
        } catch (IOException ex) {
            throw new StationBoardException(ex);
        }
    }

    /**
     * Determines if the parameter is valid
     *
     */
    private static final Predicate<Param> IS_VALID_PARAM_PREDICATE = new Predicate<Param>() {
        @Override
        public boolean apply(Param input) {
            return !Strings.isNullOrEmpty(input.name) && !Strings.isNullOrEmpty(input.value);
        }
    };

    /**
     * Converts a param into a query string
     *
     */
    private static final Function<Param, String> TO_QUERY_PARAM_FUNCTION = new Function<Param, String>() {
        @Override
        public String apply(Param input) {
            return String.format("%s=%s", input.name, input.value);
        }
    };

    /**
     * A query parameter
     *
     */
    protected static class Param {

        private final String name;

        private final String value;

        /**
         * Default constructor
         *
         * @param name the name
         * @param value the value
         */
        private Param(String name, String value) {
            this.name = name;
            this.value = value;
        }

        /**
         * Creates a parameter with the specified name and value
         *
         * @param name the name
         * @param value the value
         * @return the parameter
         */
        protected static Param create(String name, String value) {
            return new Param(name, value);
        }
    }
}
