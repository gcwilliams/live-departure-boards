package uk.co.gcwilliams.properties;

import org.jvnet.hk2.annotations.Service;

/**
 * The station codes property provider
 *
 * Created by GWilliams on 27/03/2015.
 */
@Service @StationCodes
public class StationCodesProperty implements Property {

    @Override
    public String get() {
        return System.getProperty("station-codes");
    }
}
