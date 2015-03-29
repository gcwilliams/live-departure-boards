package uk.co.gcwilliams.ldb.model.builder;

import com.google.common.base.Strings;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Station;

/**
 * The station builder
 *
 * @author Gareth Williams
 */
public class StationBuilder implements Builder<Station> {

    private String name;

    private String stationId;

    /**
     * Sets the station name
     *
     * @param name the station name
     * @return The station builder
     */
    public StationBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the station code
     *
     * @param stationId The station code
     * @return The station builder
     */
    public StationBuilder setStationId(String stationId) {
        this.stationId = stationId;
        return this;
    }

    @Override
    public Station build() {
        if (Strings.isNullOrEmpty(name)) {
            throw new IllegalStateException("A station name must be provided");
        }
        if (Strings.isNullOrEmpty(stationId)) {
            throw new IllegalStateException("A station id must be provided");
        }
        return new StationImpl(name, new Id<Station>(stationId));
    }

    private static class StationImpl implements Station {

        private final String name;

        private final Id<Station> id;

        private StationImpl(String name, Id<Station> id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Id<Station> getStationId() {
            return id;
        }
    }
}
