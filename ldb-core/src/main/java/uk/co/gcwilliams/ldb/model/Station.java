package uk.co.gcwilliams.ldb.model;

import java.io.Serializable;

/**
 * @author Gareth Williams (466567)
 */
public interface Station extends Serializable {

    /**
     * Gets the name
     *
     * @return The name
     */
    String getName();

    /**
     * Gets the station code
     *
     * @return The station code
     */
    Id<Station> getStationId();
}
