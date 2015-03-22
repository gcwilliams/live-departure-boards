package uk.co.gcwilliams.ldb.service;

import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.ServiceDetail;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;

/**
 * The station boards
 *
 * @author Gareth Williams (466567)
 */
public interface StationBoards {

    /**
     * Gets the arrival board for the specified station
     *
     * @param stationCode The station code
     * @return The arrival board
     */
    StationBoard getArrivalBoard(StationCode stationCode);

    /**
     * Gets the arrival board for the specified station from the specified station
     *
     * @param stationCode The station code
     * @param from The departure station code
     * @return The arrival board
     */
    StationBoard getArrivalBoard(StationCode stationCode, StationCode from);

    /**
     * Gets the departure board for the specified station
     *
     * @param stationCode The station code
     * @return The departure board
     */
    StationBoard getDepartureBoard(StationCode stationCode);

    /**
     * Gets the departure board for the specified station to the specified station
     *
     * @param stationCode The station code
     * @param to The destination station code
     * @return The departure board
     */
    StationBoard getDepartureBoard(StationCode stationCode, StationCode to);

    /**
     * Gets the service details
     *
     * @param serviceId The service ID
     * @return The service details
     */
    ServiceDetail getServiceDetail(Id<Service> serviceId);

}
