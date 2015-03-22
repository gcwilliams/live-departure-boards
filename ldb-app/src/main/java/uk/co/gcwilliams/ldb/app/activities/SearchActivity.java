package uk.co.gcwilliams.ldb.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import uk.co.gcwilliams.ldb.app.LdbConstants;
import uk.co.gcwilliams.ldb.app.R;
import uk.co.gcwilliams.ldb.app.adapters.StationCodeAdapter;

/**
 * The search activity, the main entry point for the application, this
 * activity allows a user to find a station board
 *
 * @author Gareth Williams (466567)
 */
@ContentView(R.layout.search)
public class SearchActivity extends RoboActivity implements View.OnClickListener {

    @Inject
    private StationCodeAdapter departingFromAdapter;

    @Inject
    private StationCodeAdapter goingToAdapter;

    @InjectView(R.id.departing_from)
    private AutoCompleteTextView departingFrom;

    @InjectView(R.id.going_to)
    private AutoCompleteTextView goingTo;

    @InjectView(R.id.view_button)
    private Button viewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        departingFrom.setAdapter(departingFromAdapter);
        goingTo.setAdapter(goingToAdapter);
        viewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (departingFromAdapter.getSelected().isPresent()) {
            Intent showBoardIntent = new Intent(this, DepartureBoardActivity.class);
            showBoardIntent.putExtra(LdbConstants.Search.FROM, departingFromAdapter.getSelected().get());
            if (goingToAdapter.getSelected().isPresent()) {
                showBoardIntent.putExtra(LdbConstants.Search.TO, goingToAdapter.getSelected().get());
            }
            startActivity(showBoardIntent);
        }
    }
}