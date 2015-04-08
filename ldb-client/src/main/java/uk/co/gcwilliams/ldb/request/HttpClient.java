package uk.co.gcwilliams.ldb.request;

/**
 * The HTTP client
 *
 * Created by GWilliams on 06/04/2015.
 */
public interface HttpClient {

    /**
     * Creates a new request
     *
     * @return the request builder
     */
    <T> RequestBuilder<T> request();
}
