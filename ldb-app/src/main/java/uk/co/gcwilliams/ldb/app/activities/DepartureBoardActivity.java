package uk.co.gcwilliams.ldb.app.activities;

import roboguice.inject.ContentView;
import uk.co.gcwilliams.ldb.app.R;
import uk.co.gcwilliams.ldb.app.tasks.AbstractStationBoardTask;
import uk.co.gcwilliams.ldb.app.tasks.DepartureStationBoardTask;

/**
 * @author Gareth Williams
 */
@ContentView(R.layout.services)
public class DepartureBoardActivity extends AbstractBoardActivity {

    @Override
    protected AbstractStationBoardTask getStationBoardTask() {
        return new DepartureStationBoardTask(handler, boards);
    }
}