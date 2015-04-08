package uk.co.gcwilliams.ldb.request;

/**
 * The HTTP client implementation
 *
 * Created by GWilliams on 06/04/2015.
 */
public class HttpClientImpl implements HttpClient {

    private final String host;

    private final String contextPath;

    private final int port;

    private final String authorizationHeader;

    /**
     * Default constructor
     *
     * @param host the host
     * @param contextPath the context path
     * @param port the port
     * @param authorizationHeader the authorization header
     */
    public HttpClientImpl(String host, String contextPath, int port, String authorizationHeader) {
        this.host = host;
        this.contextPath = contextPath;
        this.port = port;
        this.authorizationHeader = authorizationHeader;
    }

    @Override
    public <T> RequestBuilder<T> request() {
        return new RequestBuilderImpl<T>()
            .withHost(host)
            .withPort(port)
            .withContextPath(contextPath)
            .withAuthorization(authorizationHeader);
    }
}
