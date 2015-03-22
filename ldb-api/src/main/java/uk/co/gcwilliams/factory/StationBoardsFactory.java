package uk.co.gcwilliams.factory;

import org.glassfish.hk2.api.Factory;
import uk.co.gcwilliams.ldb.service.StationBoards;
import uk.co.gcwilliams.ldb.service.StationBoardsImpl;
import uk.co.gcwilliams.ldb.stubs.Ldb;

/**
 * @author Gareth Williams (466567)
 */
public class StationBoardsFactory implements Factory<StationBoards> {

    private final StationBoards boards = new StationBoardsImpl(new Ldb().getLDBServiceSoap12(), System.getProperty("ldb-key"));

    @Override
    public StationBoards provide() {
        return boards;
    }

    @Override
    public void dispose(StationBoards stationBoards) { }
}
