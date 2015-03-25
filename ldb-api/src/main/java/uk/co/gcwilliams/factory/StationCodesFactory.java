package uk.co.gcwilliams.factory;

import org.glassfish.hk2.api.Factory;
import uk.co.gcwilliams.service.StationCodesService;
import uk.co.gcwilliams.service.StationCodesServiceImpl;

/**
 * The station codes factory
 *
 * @author Gareth Williams (466567)
 */
public class StationCodesFactory implements Factory<StationCodesService> {

    private final StationCodesService stationCodesService = new StationCodesServiceImpl();

    @Override
    public StationCodesService provide() {
        return stationCodesService;
    }

    @Override
    public void dispose(StationCodesService stationCodesService) { }
}
