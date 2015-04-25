package uk.co.gcwilliams.ldb.app.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import uk.co.gcwilliams.ldb.app.LdbConstants;
import uk.co.gcwilliams.ldb.app.R;
import uk.co.gcwilliams.ldb.app.boards.StationBoardProvider;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;

import javax.inject.Inject;

/**
 * The service detail activity
 *
 * @author Gareth Williams
 */
@ContentView(R.layout.service_detail)
public class ServiceDetailsActivity extends RoboActivity implements Handler.Callback {

    @Inject
    protected StationBoardProvider provider;

    private final Handler handler = new Handler(this);

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        provider.getServiceDetail((Id<Service>) bundle.getSerializable(LdbConstants.Detail.SERVICE_ID), handler);
    }

    @Override
    public boolean handleMessage(Message msg) {

        return false;
    }
}
