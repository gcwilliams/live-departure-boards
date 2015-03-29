package uk.co.gcwilliams.ldb.service;

import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.ServiceDetail;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;

/**
 * The station boards
 *
 * @author Gareth Williams
 */
public interface StationBoards {

    /**
     * Gets the arrival board for the specified station
     *
     * @param to The station code
     * @return The arrival board
     */
    StationBoard getArrivalBoard(StationCode to);

    /**
     * Gets the arrival board for the specified station from the specified station
     *
     * @param to The station code
     * @param from The departure station code
     * @return The arrival board
     */
    StationBoard getArrivalBoard(StationCode to, StationCode from);

    /**
     * Gets the departure board for the specified station
     *
     * @param from The station code
     * @return The departure board
     */
    StationBoard getDepartureBoard(StationCode from);

    /**
     * Gets the departure board for the specified station to the specified station
     *
     * @param from The station code
     * @param to The destination station code
     * @return The departure board
     */
    StationBoard getDepartureBoard(StationCode from, StationCode to);

    /**
     * Gets the service details
     *
     * @param id The service ID
     * @return The service details
     */
    ServiceDetail getServiceDetail(Id<Service> id);

}
