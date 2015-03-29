package uk.co.gcwilliams.ldb.app.tasks;

import android.os.Handler;
import com.google.common.base.Optional;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.service.StationBoards;

import javax.inject.Inject;

/**
 * The departure station board task
 *
 * @author Gareth Williams
 */
public class DepartureStationBoardTask extends AbstractStationBoardTask {

    /**
     * Default constructor
     *
     * @param handler The handler
     * @param boards The station boards
     */
    @Inject
    public DepartureStationBoardTask(Handler handler, StationBoards boards) {
        super(handler, boards);
    }

    @Override
    protected StationBoard getBoard(StationCode from, Optional<StationCode> to) {
        return to.isPresent()
            ? boards.getDepartureBoard(from, to.get())
            : boards.getDepartureBoard(from);
    }
}
