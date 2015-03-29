package uk.co.gcwilliams.ldb.app.tasks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.common.base.Optional;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.service.StationBoards;

/**
 * @author Gareth Williams
 */
public abstract class AbstractStationBoardTask extends AsyncTask<StationCode, Void, StationBoard> {

    public static final int STATION_BOARD = 1254554545;

    public static final String BOARD = "board";

    private final Handler handler;

    protected final StationBoards boards;

    protected AbstractStationBoardTask(Handler handler, StationBoards boards) {
        this.handler = handler;
        this.boards = boards;
    }

    @Override
    protected StationBoard doInBackground(StationCode... codes) {
        if (codes.length < 1 || codes.length > 2) {
            throw new IllegalArgumentException("1 or 2 station codes must be provided");
        }
        return getBoard(codes[0], codes.length == 2 ? Optional.of(codes[1]) : Optional.<StationCode>absent());
    }

    @Override
    protected void onPostExecute(StationBoard board) {
        Message message = handler.obtainMessage(STATION_BOARD);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BOARD, board);
        message.setData(bundle);
        message.setTarget(handler);
        message.sendToTarget();
    }

    protected abstract StationBoard getBoard(StationCode from, Optional<StationCode> to);
}
