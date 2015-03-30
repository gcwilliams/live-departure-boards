package uk.co.gcwilliams.properties;

import org.jvnet.hk2.annotations.Contract;

/**
 * The property provider
 *
 * Created by GWilliams on 27/03/2015.
 */
@Contract
public interface Property {

    /**
     * Gets the property
     *
     * @return the property
     */
    String get();
}
