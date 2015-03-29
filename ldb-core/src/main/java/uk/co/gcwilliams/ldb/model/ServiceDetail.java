package uk.co.gcwilliams.ldb.model;

import org.joda.time.DateTime;

import java.util.List;

/**
 * The service details
 *
 * @author Gareth Williams
 */
public interface ServiceDetail extends ServiceInformation {

    /**
     * Gets the generated at
     *
     * @return The generated at
     */
    DateTime getGeneratedAt();

    /**
     * Gets the station
     *
     * @return The station
     */
    Station getCurrentStation();

    /**
     * Gets the previous calling points
     *
     * @return The previous calling points
     */
    List<List<PreviousCallingPoint>> getPreviousCallingPoints();

    /**
     * Gets the subsequent calling points
     *
     * @return The subsequent calling points
     */
    List<List<SubsequentCallingPoint>> getSubsequentCallingPoints();

}
