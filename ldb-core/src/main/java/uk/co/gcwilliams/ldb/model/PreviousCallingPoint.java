package uk.co.gcwilliams.ldb.model;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

/**
 * The previous calling point
 *
 * @author Gareth Williams
 */
public interface PreviousCallingPoint extends CallingPoint {

    /**
     * The actual departure time
     *
     * @return The actual departure time
     */
    Optional<DateTime> getActualDepartureTime();
}
