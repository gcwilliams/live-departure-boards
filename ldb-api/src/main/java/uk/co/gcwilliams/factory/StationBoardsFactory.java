package uk.co.gcwilliams.factory;

import org.glassfish.hk2.api.Factory;
import uk.co.gcwilliams.ldb.service.StationBoards;
import uk.co.gcwilliams.ldb.service.StationBoardsImpl;
import uk.co.gcwilliams.ldb.stubs.LDBServiceSoap;
import uk.co.gcwilliams.properties.LdbKey;
import uk.co.gcwilliams.properties.Property;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The station boards factory
 *
 * @author Gareth Williams (466567)
 */
public class StationBoardsFactory implements Factory<StationBoards> {

    private final StationBoards boards;

    /**
     * Default constructor
     *
     * @param ldbServiceSoap the LDB soap service
     * @param ldbKeyProperty the LDB key property
     */
    @Inject
    public StationBoardsFactory(LDBServiceSoap ldbServiceSoap, @LdbKey Property ldbKeyProperty) {
        boards = new StationBoardsImpl(ldbServiceSoap, ldbKeyProperty.get());
    }

    @Override
    @Singleton
    public StationBoards provide() {
        return boards;
    }

    @Override
    public void dispose(StationBoards stationBoards) { }
}
