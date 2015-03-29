package uk.co.gcwilliams.ldb.service;

import uk.co.gcwilliams.ldb.model.StationCode;

import java.util.List;

/**
 * The station codes
 *
 * @author Gareth Williams
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
