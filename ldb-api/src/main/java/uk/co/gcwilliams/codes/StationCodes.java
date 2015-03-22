package uk.co.gcwilliams.codes;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.model.builder.StationCodeBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The station codes
 *
 * @author Gareth Williams (466567)
 */
public class StationCodes {

    private static final Pattern STATION_PATTERN = Pattern.compile("^(.*),(.*)$");

    private static final List<StationCode> stationCodes = Lists.newArrayList();

    static {
        try {
            InputStream inputStream = StationCodes.class.getResourceAsStream("station-codes.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = STATION_PATTERN.matcher(line);
                if (!matcher.matches()) {
                    throw new RuntimeException("Unable to initiate station codes");
                }
                stationCodes.add(new StationCodeBuilder()
                    .setName(matcher.group(1).trim())
                    .setStationId(matcher.group(2).trim())
                    .build()
                );
            }
        } catch (IOException ex) {
            throw new RuntimeException("Unable to initiate station codes");
        }
    }

    /**
     * Find the station with the specified code
     *
     * @param code The code
     * @return The station
     */
    public Optional<StationCode> getCode(final String code) {
        return Iterables.tryFind(stationCodes, new Predicate<StationCode>() {
            @Override
            public boolean apply(StationCode input) {
                return input.getStationId().get().equals(code);
            }
        });
    }

    /**
     * Gets all the station codes
     *
     * @return The station codes
     */
    public List<StationCode> getCodes() {
        return Collections.unmodifiableList(stationCodes);
    }
}
