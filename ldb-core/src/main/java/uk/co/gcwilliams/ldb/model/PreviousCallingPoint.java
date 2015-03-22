package uk.co.gcwilliams.ldb.model;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

/**
 * @author Gareth Williams (466567)
 */
public interface PreviousCallingPoint extends CallingPoint {

    /**
     * The actual departure time
     *
     * @return The actual departure time
     */
    Optional<DateTime> getActualDepartureTime();
}
