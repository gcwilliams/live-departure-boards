package uk.co.gcwilliams.ldb.request;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;
import org.apache.http.message.BasicNameValuePair;
import uk.co.gcwilliams.ldb.service.StationBoardException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static org.apache.http.client.utils.URLEncodedUtils.format;

/**
 * The URL builder
 *
 * Created by GWilliams on 06/04/2015.
 */
public class RequestBuilderImpl<T> implements RequestBuilder<T> {

    private static final String HTTPS = "https";

    private final List<BasicNameValuePair> params = Lists.newArrayList();

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
    public RequestBuilder<T> withPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public RequestBuilder<T> withParam(String name, String value) {
        this.params.add(new BasicNameValuePair(name, value != null ? value.trim() : ""));
        return this;
    }

    @Override
    public T execute(Function<String, T> builder) {
        URLConnection connection = null;
        try {
            URL url = new URI(HTTPS, null, host, port, contextPath + path, format(params, Charsets.UTF_8.name()), null).toURL();
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
}
