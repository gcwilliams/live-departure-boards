package uk.co.gcwilliams.ldb.model;

import java.io.Serializable;

/**
 * The station code
 *
 * @author Gareth Williams (466567)
 */
public interface StationCode extends Serializable {

    /**
     * Gets the station name
     *
     * @return The station name
     */
    String getName();

    /**
     * Gets the station ID
     *
     * @return The station ID
     */
    Id<Station> getStationId();
}
