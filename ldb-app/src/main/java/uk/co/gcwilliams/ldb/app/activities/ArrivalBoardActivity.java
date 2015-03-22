package uk.co.gcwilliams.ldb.app.activities;

import roboguice.inject.ContentView;
import uk.co.gcwilliams.ldb.app.R;
import uk.co.gcwilliams.ldb.app.tasks.AbstractStationBoardTask;
import uk.co.gcwilliams.ldb.app.tasks.ArrivalStationBoardTask;

/**
 * @author Gareth Williams (466567)
 */
@ContentView(R.layout.services)
public class ArrivalBoardActivity extends AbstractBoardActivity {

    @Override
    protected AbstractStationBoardTask getStationBoardTask() {
        return new ArrivalStationBoardTask(handler, boards);
    }
}
