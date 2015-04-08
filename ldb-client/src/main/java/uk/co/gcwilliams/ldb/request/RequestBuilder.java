package uk.co.gcwilliams.ldb.request;

import com.google.common.base.Function;

/**
 * The request builder
 *
 * Created by GWilliams on 06/04/2015.
 */
public interface RequestBuilder<T> {

    /**
     * Adds the path
     *
     * @param path the path
     * @return the builder
     */
    RequestBuilder<T> withPath(String path);

    /**
     * Adds the parameter
     *
     * @param name the name of the parameter
     * @param value the value of the parameter
     * @return the  builder
     */
    RequestBuilder<T> withParam(String name, String value);

    /**
     * Builds the URL
     *
     * @return The URI builder
     */
    T execute(Function<String, T> builder);
}
