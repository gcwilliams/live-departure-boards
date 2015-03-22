package uk.co.gcwilliams.ldb.service;

import uk.co.gcwilliams.ldb.model.StationCode;

import java.util.List;

/**
 * @author Gareth Williams (466567)
 */
public interface StationCodes {

    /**
     * Suggests a list of station codes
     *
     * @param term The search term
     * @return The list of suggested station codes
     */
    List<StationCode> suggestStationCodes(String term);

}
