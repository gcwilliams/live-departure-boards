package uk.co.gcwilliams.ldb.app.boards;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.common.base.Optional;
import uk.co.gcwilliams.ldb.app.LdbConstants;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.service.StationBoards;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.concurrent.Executor;

/**
 * The station board provider implementation
 *
 * Created by GWilliams on 20/04/2015.
 */
public class StationBoardProviderImpl implements StationBoardProvider {

    private final Executor executor;

    private final StationBoards boards;

    /**
     * Default constructor
     *
     * @param boards the station boards
     */
    @Inject
    public StationBoardProviderImpl(StationBoards boards, Executor executor) {
        this.boards = boards;
        this.executor = executor;
    }

    @Override
    public void getArrivalsBoard(final StationCode to, final Optional<StationCode> from, final Handler handler) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                sendMessage(handler, LdbConstants.Search.BOARD_MSG_ID, LdbConstants.Search.BOARD, from.isPresent()
                    ? boards.getArrivalBoard(to, from.get())
                    : boards.getArrivalBoard(to)
                );
            }
        });
    }

    @Override
    public void getDeparturesBoard(final StationCode from, final Optional<StationCode> to, final Handler handler) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                sendMessage(handler, LdbConstants.Search.BOARD_MSG_ID, LdbConstants.Search.BOARD, to.isPresent()
                    ? boards.getDepartureBoard(from, to.get())
                    : boards.getDepartureBoard(from)
                );
            }
        });
    }

    @Override
    public void getServiceDetail(final Id<Service> serviceId, final Handler handler) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                sendMessage(
                    handler,
                    LdbConstants.Detail.SERVICE_DETAIL_MSG_ID,
                    LdbConstants.Detail.SERVICE_DETAIL,
                    boards.getServiceDetail(serviceId)
                );
            }
        });
    }

    /**
     * Sends the message to the handler
     *
     * @param messageId the message ID
     * @param key the key to store the data with
     * @param data the data
     */
    private static <T extends Serializable> void sendMessage(Handler handler, int messageId, String key, T data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, data);
        Message msg = handler.obtainMessage(messageId);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }
}
