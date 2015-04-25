package uk.co.gcwilliams.ldb.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import uk.co.gcwilliams.ldb.app.LdbConstants;
import uk.co.gcwilliams.ldb.app.R;
import uk.co.gcwilliams.ldb.app.adapters.StationCodeAdapter;
import uk.co.gcwilliams.ldb.model.StationCode;

/**
 * The search activity, the main entry point for the application, this
 * activity allows a user to find a station board
 *
 * @author Gareth Williams
 */
@ContentView(R.layout.search)
public class SearchActivity extends RoboActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Inject
    private InputMethodManager inputMethodManager;

    @Inject
    private StationCodeAdapter stationCodeAdapter;

    @Inject
    private StationCodeAdapter optionalStationCodeAdapter;

    @InjectView(R.id.station_code)
    private AutoCompleteTextView stationCode;

    @InjectView(R.id.optional_station_code)
    private AutoCompleteTextView optionalStationCode;

    @InjectView(R.id.departures)
    private RadioButton departures;

    @InjectView(R.id.view_button)
    private Button viewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        restoreSelectedStationCode(savedInstanceState, stationCodeAdapter, LdbConstants.Search.STATION_CODE);
//        restoreSelectedStationCode(savedInstanceState, optionalStationCodeAdapter, LdbConstants.Search.OPTIONAL_STATION_CODE);
        stationCode.setOnItemClickListener(this);
        stationCode.setAdapter(stationCodeAdapter);
        optionalStationCode.setOnItemClickListener(this);
        optionalStationCode.setAdapter(optionalStationCodeAdapter);
        viewButton.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
//        saveSelectedStationCode(savedInstanceState, stationCodeAdapter, LdbConstants.Search.STATION_CODE);
//        saveSelectedStationCode(savedInstanceState, optionalStationCodeAdapter, LdbConstants.Search.OPTIONAL_STATION_CODE);
    }

    @Override
    public void onClick(View v) {
        if (stationCodeAdapter.getSelected().isPresent()) {
            Intent showBoardIntent = new Intent(this, StationBoardActivity.class);
            showBoardIntent.putExtra(LdbConstants.Search.DEPARTURES, departures.isChecked());
            showBoardIntent.putExtra(LdbConstants.Search.STATION_CODE, stationCodeAdapter.getSelected().get());
            showBoardIntent.putExtra(LdbConstants.Search.OPTIONAL_STATION_CODE, optionalStationCodeAdapter.getSelected());
            startActivity(showBoardIntent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    /**
     * The on board type changed
     *
     * @param view the view
     */
    public void onBoardTypeChanged(View view) {
        switch (view.getId()) {
            case R.id.departures:
                stationCode.setHint(R.string.departures_from);
                optionalStationCode.setHint(R.string.departures_to);
                break;
            case R.id.arrivals:
                stationCode.setHint(R.string.arrivals_to);
                optionalStationCode.setHint(R.string.arrivals_from);
                break;
        }
    }

    /**
     * Restores the selected station code
     *
     * @param savedInstanceState the bundle to restore the code from
     * @param adapter the adapter to set the code on
     * @param stationCodeKey the station code key
     */
    private static void restoreSelectedStationCode(Bundle savedInstanceState, StationCodeAdapter adapter, String stationCodeKey) {
        StationCode code = savedInstanceState != null ? (StationCode)savedInstanceState.getSerializable(stationCodeKey) : null;
        if (code != null) {
            adapter.setSelected(code);
        }
    }

    /**
     * Saves the selected station code
     *
     * @param savedInstanceState the bundle to save the code into
     * @param adapter the adapter to get the code from
     * @param stationCodeKey the station code key
     */
    private static void saveSelectedStationCode(Bundle savedInstanceState, StationCodeAdapter adapter, String stationCodeKey) {
        Optional<StationCode> code = adapter.getSelected();
        if (code.isPresent()) {
            savedInstanceState.putSerializable(stationCodeKey, code.get());
        }
    }
}