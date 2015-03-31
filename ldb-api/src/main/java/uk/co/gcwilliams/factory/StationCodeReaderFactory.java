package uk.co.gcwilliams.factory;

import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Service;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.model.builder.StationCodeBuilder;
import uk.co.gcwilliams.logging.Loggable;
import uk.co.gcwilliams.properties.Property;
import uk.co.gcwilliams.properties.StationCodes;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Stream.of;
import static uk.co.gcwilliams.util.ImmutableCollectors.toImmutableList;

/**
 * The file line mapper
 *
 * Created by GWilliams on 27/03/2015.
 */
@Service @Loggable
public class StationCodeReaderFactory implements Factory<List<StationCode>> {

    private static final Pattern STATION_PATTERN = Pattern.compile("^(.+)=(.+)$");

    private static final Function<String, Stream<StationCode>> STATION_CODE_MAPPER = (l) -> of(l)
        .map(STATION_PATTERN::matcher)
        .filter(Matcher::matches)
        .map((m) -> new StationCodeBuilder().setName(m.group(1).trim()).setStationId(m.group(2).trim()).build());

    private final Property stationCodesProperty;

    /**
     * Default constructor
     *
     * @param stationCodesProperty the station codes property
     */
    @Inject
    public StationCodeReaderFactory(@StationCodes Property stationCodesProperty) {
        this.stationCodesProperty = stationCodesProperty;
    }

    @Override
    @Singleton
    public List<StationCode> provide() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(stationCodesProperty.get()))))) {
            return reader.lines().map(STATION_CODE_MAPPER).flatMap(identity()).collect(toImmutableList());
        } catch (IOException ex) {
            throw new RuntimeException(format("Unable to stream file %s", stationCodesProperty.get()), ex);
        }
    }

    @Override
    public void dispose(List<StationCode> stationCodes) { }
}
