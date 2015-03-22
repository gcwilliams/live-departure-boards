package uk.co.gcwilliams.ldb.service.impl;

/**
 * The station board exception
 *
 * @author Gareth Williams (466567)
 */
public class StationBoardException extends RuntimeException {

    /**
     * Default constructor
     *
     * @param ex The cause of the exception
     */
    public StationBoardException(Exception ex) {
        super(ex);
    }
}
