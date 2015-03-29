package uk.co.gcwilliams.ldb.model;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * The station board
 *
 * @author Gareth Williams
 */
public interface StationBoard extends Serializable {

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
    Station getStation();

    /**
     * Gets the services
     *
     * @return The services
     */
    List<Service> getServices();
}
