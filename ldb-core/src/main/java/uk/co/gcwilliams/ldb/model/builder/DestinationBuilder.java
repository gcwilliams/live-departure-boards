package uk.co.gcwilliams.ldb.model.builder;

import com.google.common.base.Optional;
import uk.co.gcwilliams.ldb.model.Destination;
import uk.co.gcwilliams.ldb.model.Station;

/**
 * The location builder
 *
 * @author Gareth Williams (466567)
 */
public class DestinationBuilder implements Builder<Destination> {

    private StationBuilder station;

    private String via;

    /**
     * Sets the station
     *
     * @param station The station
     * @return The location builder
     */
    public DestinationBuilder setStation(StationBuilder station) {
        this.station = station;
        return this;
    }

    /**
     * Sets the station
     *
     * @param via The via
     * @return The station builder
     */
    public DestinationBuilder setStation(String via) {
        this.via = via;
        return this;
    }

    @Override
    public Destination build() {
        if (station == null) {
            throw new IllegalStateException("A station must be provided");
        }
        return new DestinationImpl(station.build(), Optional.fromNullable(via));
    }

    private static class DestinationImpl implements Destination {

        private final Station station;

        private final Optional<String> via;

        private DestinationImpl(Station station, Optional<String> via) {
            this.station = station;
            this.via = via;
        }

        @Override
        public Station getStation() {
            return station;
        }

        @Override
        public Optional<String> getVia() {
            return via;
        }
    }
}
