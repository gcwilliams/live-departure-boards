package uk.co.gcwilliams.ldb.model.builder;

import org.joda.time.DateTime;
import uk.co.gcwilliams.ldb.model.CallingPoint;
import uk.co.gcwilliams.ldb.model.Station;

/**
 * The abstract calling point builder
 *
 * @author Gareth Williams (466567)
 */
public abstract class AbstractCallingPointBuilder {

    protected StationBuilder station;

    protected DateTime standardDepartureTime;

    /**
     * Sets the station
     *
     * @param station The station
     * @return The calling point builder
     */
    public AbstractCallingPointBuilder setStation(StationBuilder station) {
        this.station = station;
        return this;
    }

    /**
     * Sets the standard departure time
     *
     * @param standardDepartureTime The standard departure time
     * @return The calling point builder
     */
    public AbstractCallingPointBuilder setStandardDepartureTime(DateTime standardDepartureTime) {
        this.standardDepartureTime = standardDepartureTime;
        return this;
    }
}
