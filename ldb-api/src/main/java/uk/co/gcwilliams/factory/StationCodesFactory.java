package uk.co.gcwilliams.factory;

import org.glassfish.hk2.api.Factory;
import uk.co.gcwilliams.codes.StationCodes;

/**
 * The station codes factory
 *
 * @author Gareth Williams (466567)
 */
public class StationCodesFactory implements Factory<StationCodes> {

    private final StationCodes codes = new StationCodes();

    @Override
    public StationCodes provide() {
        return codes;
    }

    @Override
    public void dispose(StationCodes stationCodes) { }
}
