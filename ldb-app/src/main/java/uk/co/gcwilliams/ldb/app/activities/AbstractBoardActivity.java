package uk.co.gcwilliams.ldb.app.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.google.common.base.Optional;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import uk.co.gcwilliams.ldb.app.LdbConstants;
import uk.co.gcwilliams.ldb.app.R;
import uk.co.gcwilliams.ldb.app.adapters.ServicesAdapter;
import uk.co.gcwilliams.ldb.app.tasks.AbstractStationBoardTask;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.service.StationBoards;

import javax.inject.Inject;

/**
 * @author Gareth Williams (466567)
 */
public abstract class AbstractBoardActivity extends RoboActivity implements Handler.Callback {

    protected final Handler handler = new Handler(this);

    @Inject
    protected StationBoards boards;

    @Inject
    private LayoutInflater inflater;

    @InjectView(R.id.departing_from)
    private TextView departingFrom;

    @InjectView(R.id.going_to)
    private TextView goingTo;

    @InjectView(R.id.services)
    private ListView services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        StationCode from = (StationCode)bundle.getSerializable(LdbConstants.Search.FROM);
        Optional<StationCode> to = Optional.fromNullable((StationCode)bundle.getSerializable(LdbConstants.Search.TO));

        departingFrom.setText(from.getName());
        goingTo.setText(to.isPresent() ? to.get().getName() : "");
        goingTo.setVisibility(to.isPresent() ? View.VISIBLE : View.GONE);

        AbstractStationBoardTask boardTask = getStationBoardTask();
        if (to.isPresent()) {
            boardTask.execute(from, to.get());
        } else {
            boardTask.execute(from);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        StationBoard board = (StationBoard)msg.getData().getSerializable(AbstractStationBoardTask.BOARD);
        services.setAdapter(new ServicesAdapter(board.getServices(), inflater));
        return true;
    }

    protected abstract AbstractStationBoardTask getStationBoardTask();
}
