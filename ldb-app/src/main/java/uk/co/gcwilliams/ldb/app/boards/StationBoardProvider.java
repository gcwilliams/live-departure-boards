package uk.co.gcwilliams.ldb.app.boards;

import android.os.Handler;
import com.google.common.base.Optional;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.StationCode;


/**
 * The board provider
 *
 * Created by GWilliams on 18/04/2015.
 */
public interface StationBoardProvider {

    /**
     * Gets the arrival board
     *
     * @param to the to station
     * @param from the from station
     * @param handler the handler
     */
    void getArrivalsBoard(StationCode to, Optional<StationCode> from, Handler handler);

    /**
     * Gets the departures board
     *
     * @param from the from station
     * @param to the to station
     * @param handler the handler
     */
    void getDeparturesBoard(StationCode from, Optional<StationCode> to, Handler handler);

    /**
     * Gets the service detail
     *
     * @param serviceId the service ID
     * @param handler the handler
     */
    void getServiceDetail(Id<Service> serviceId, Handler handler);
}
