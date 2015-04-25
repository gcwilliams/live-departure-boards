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
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import uk.co.gcwilliams.ldb.app.LdbConstants;
import uk.co.gcwilliams.ldb.app.R;
import uk.co.gcwilliams.ldb.app.adapters.ServicesAdapter;
import uk.co.gcwilliams.ldb.app.boards.StationBoardProvider;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;

import javax.inject.Inject;

/**
 * The station board activity
 *
 * @author Gareth Williams
 */
@ContentView(R.layout.services)
public class StationBoardActivity extends RoboActivity implements Handler.Callback {

    @Inject
    private StationBoardProvider provider;

    private final Handler handler = new Handler(this);

    @Inject
    private LayoutInflater inflater;

    @InjectView(R.id.station_code)
    private TextView stationCode;

    @InjectView(R.id.optional_station_code)
    private TextView optionalStationCode;

    @InjectView(R.id.services)
    private ListView services;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        StationCode stationCode = (StationCode)bundle.getSerializable(LdbConstants.Search.STATION_CODE);
        Optional<StationCode> optionalStationCode = (Optional<StationCode>)bundle.getSerializable(LdbConstants.Search.OPTIONAL_STATION_CODE);

        this.stationCode.setText(stationCode.getName());
        this.optionalStationCode.setText(optionalStationCode.isPresent() ? optionalStationCode.get().getName() : "");
        this.optionalStationCode.setVisibility(optionalStationCode.isPresent() ? View.VISIBLE : View.GONE);

        if (bundle.getBoolean(LdbConstants.Search.DEPARTURES)) {
            provider.getDeparturesBoard(stationCode, optionalStationCode, handler);
        } else {
            provider.getArrivalsBoard(stationCode, optionalStationCode, handler);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        StationBoard board = (StationBoard)msg.getData().getSerializable(StationBoardProvider.BOARD);
        services.setAdapter(new ServicesAdapter(board.getServices(), inflater));
        return true;
    }
}
