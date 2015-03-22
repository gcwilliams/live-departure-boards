package uk.co.gcwilliams.ldb.model;

import com.google.common.base.Optional;

import java.io.Serializable;

/**
 * The location, represents a departure or destination
 *
 * @author Gareth Williams (466567)
 */
public interface Destination extends Serializable {

    /**
     * Gets the station
     *
     * @return The station
     */
    Station getStation();

    /**
     * Gets the via
     *
     * @return The via
     */
    Optional<String> getVia();
}
