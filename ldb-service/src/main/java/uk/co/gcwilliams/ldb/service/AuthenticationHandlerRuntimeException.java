package uk.co.gcwilliams.ldb.service;

/**
 * The authentication handler runtime exception, occurs when the authentication
 * token cannot be set for the soap request
 *
 * @author Gareth Williams
 */
public class AuthenticationHandlerRuntimeException extends RuntimeException {

    /**
     * Default constructor
     *
     * @param throwable The throwable
     */
    public AuthenticationHandlerRuntimeException(Throwable throwable) {
        super(throwable);
    }
}
