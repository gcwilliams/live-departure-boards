package uk.co.gcwilliams.ldb.request;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import uk.co.gcwilliams.ldb.service.StationBoardException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;

/**
 * The URL builder
 *
 * Created by GWilliams on 06/04/2015.
 */
public class RequestBuilderImpl<T> implements RequestBuilder<T> {

    private static final String HTTPS = "https";

    private static final Joiner PARAMETER_JOINER = Joiner.on("&");

    private final List<Param> params = Lists.newArrayList();

    private String host;

    private int port = 80;

    private String contextPath = "";

    private String path;

    private String authorizationHeader = "";

    /**
     * Adds the host
     *
     * @param host the host
     * @return the  builder
     */
    public RequestBuilderImpl<T> withHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * Adds the port
     *
     * @param port the port
     * @return the  builder
     */
    public RequestBuilderImpl<T> withPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Adds the context path
     *
     * @param contextPath the context path
     * @return the  builder
     */
    public RequestBuilderImpl<T> withContextPath(String contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    /**
     * Adds the authorization header
     *
     * @param authorizationHeader the authorization header
     * @return the builder
     */
    public RequestBuilderImpl<T> withAuthorization(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
        return this;
    }

    @Override
    public RequestBuilderImpl<T> withPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public RequestBuilderImpl<T> withParam(String name, String value) {
        this.params.add(new Param(name, value));
        return this;
    }

    @Override
    public T execute(Function<String, T> builder) {
        Iterable<String> parameters = transform(filter(params, IS_VALID_PARAM_PREDICATE), TO_QUERY_PARAM_FUNCTION);
        URLConnection connection = null;
        try {
            URL url = new URI(HTTPS, null, host, port, contextPath + path, PARAMETER_JOINER.join(parameters), null).toURL();
            connection = url.openConnection();
            connection.setRequestProperty(HttpHeaders.ACCEPT, MediaType.JSON_UTF_8.toString());
            connection.setRequestProperty(HttpHeaders.AUTHORIZATION, authorizationHeader);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            String body = CharStreams.toString(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));
            return builder.apply(body);
        } catch (URISyntaxException | IOException ex) {
            throw new StationBoardException(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.getInputStream().close();
                } catch (IOException ex) {
                    throw new StationBoardException(ex);
                }
            }
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
    private static class Param {

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
    }
}
