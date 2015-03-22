package uk.co.gcwilliams.ldb.model;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * The calling point
 *
 * @author Gareth Williams (466567)
 */
public interface CallingPoint extends Serializable {

    /**
     * Gets the station
     *
     * @return The station
     */
    Station getStation();

    /**
     * Gets the standard departure time
     *
     * @return The standard departure time
     */
    Optional<DateTime> getStandardDepartureTime();
}
