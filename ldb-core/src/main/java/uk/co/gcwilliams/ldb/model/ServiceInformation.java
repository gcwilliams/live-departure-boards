package uk.co.gcwilliams.ldb.model;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * The service information
 *
 * @author Gareth Williams (466567)
 */
public interface ServiceInformation extends Serializable {

    /**
     * Gets the standard time of arrival
     *
     * @return The standard time of arrival
     */
    Optional<DateTime> getStandardTimeOfArrival();

    /**
     * Gets the estimated time of arrival
     *
     * @return The estimated time of arrival
     */
    Optional<DateTime> getEstimatedTimeOfArrival();

    /**
     * Gets the standard time of departure
     *
     * @return The standard time of departure
     */
    Optional<DateTime> getStandardTimeOfDeparture();

    /**
     * Gets the estimated time of departure
     *
     * @return The estimated time of departure
     */
    Optional<DateTime> getEstimatedTimeOfDeparture();

    /**
     * Gets the platform
     *
     * @return The platform
     */
    Optional<Integer> getPlatform();

    /**
     * Gets the operator
     *
     * @return The operator
     */
    String getOperator();

    /**
     * Gets the operator code
     *
     * @return The operator code
     */
    String getOperatorCode();
}
