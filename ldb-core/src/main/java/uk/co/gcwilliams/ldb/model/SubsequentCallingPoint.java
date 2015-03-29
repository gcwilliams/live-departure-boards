package uk.co.gcwilliams.ldb.model;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

/**
 * The subsequent calling point
 *
 * @author Gareth Williams
 */
public interface SubsequentCallingPoint extends CallingPoint {

    /**
     * Gets the estimated departure time
     *
     * @return The estimated departure time
     */
    Optional<DateTime> getEstimatedDepartureTime();
}
