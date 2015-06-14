package uk.co.gcwilliams.ldb.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
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
public class SearchActivity extends RoboActivity implements View.OnClickListener, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {

    @Inject
    private InputMethodManager inputMethodManager;

    @Inject
    private StationCodeAdapter stationCodeAdapter;

    @Inject
    private StationCodeAdapter optionalStationCodeAdapter;

    @InjectView(R.id.title)
    private TextView title;

    @InjectView(R.id.station_code_title)
    private TextView stationCodeTitle;

    @InjectView(R.id.station_code)
    private AutoCompleteTextView stationCode;

    @InjectView(R.id.optional_station_code_title)
    private TextView optionalStationCodeTitle;

    @InjectView(R.id.optional_station_code)
    private AutoCompleteTextView optionalStationCode;

    @InjectView(R.id.departures)
    private RadioButton departures;

    @InjectView(R.id.arrivals)
    private RadioButton arrivals;

    @InjectView(R.id.view_button)
    private Button viewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreBoardOption(savedInstanceState);
        restoreSelectedStationCode(savedInstanceState, stationCodeAdapter, LdbConstants.Search.STATION_CODE);
        restoreSelectedStationCode(savedInstanceState, optionalStationCodeAdapter, LdbConstants.Search.OPTIONAL_STATION_CODE);
        stationCode.setOnItemClickListener(this);
        stationCode.setAdapter(stationCodeAdapter);
        optionalStationCode.setOnItemClickListener(this);
        optionalStationCode.setAdapter(optionalStationCodeAdapter);
        departures.setOnCheckedChangeListener(this);
        viewButton.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        saveBoardOption(savedInstanceState);
        saveSelectedStationCode(savedInstanceState, stationCodeAdapter, LdbConstants.Search.STATION_CODE);
        saveSelectedStationCode(savedInstanceState, optionalStationCodeAdapter, LdbConstants.Search.OPTIONAL_STATION_CODE);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isDepartures) {
        setViewForBoardOption(isDepartures);
    }

    /**
     * Sets the view for the board option
     *
     * @param isDepartures true if the board is departures, false otherwise
     */
    private void setViewForBoardOption(boolean isDepartures) {
        title.setText(isDepartures ? R.string.departures : R.string.arrivals);
        stationCodeTitle.setText(isDepartures ? R.string.from : R.string.to);
        optionalStationCodeTitle.setText(isDepartures ? R.string.to : R.string.from);
        departures.setChecked(isDepartures);
        arrivals.setChecked(!isDepartures);
    }

    /**
     * Restores the board option
     *
     * @param savedInstanceState the saved instance state
     */
    private void restoreBoardOption(Bundle savedInstanceState) {
        setViewForBoardOption(Boolean.TRUE.equals(savedInstanceState == null || savedInstanceState.getBoolean(LdbConstants.Search.DEPARTURES)));
    }

    /**
     * Restores the board option
     *
     * @param savedInstanceState the saved instance state
     */
    private void saveBoardOption(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(LdbConstants.Search.DEPARTURES, departures.isChecked());
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