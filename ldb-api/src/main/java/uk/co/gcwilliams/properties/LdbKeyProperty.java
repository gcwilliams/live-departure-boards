package uk.co.gcwilliams.properties;

import org.jvnet.hk2.annotations.Service;
import uk.co.gcwilliams.logging.Loggable;

/**
 * The LDB key system property provider
 *
 * Created by GWilliams on 27/03/2015.
 */
@Service @LdbKey
public class LdbKeyProperty implements Property {

    @Override
    @Loggable
    public String get() {
        return System.getProperty("ldb-key");
    }
}
