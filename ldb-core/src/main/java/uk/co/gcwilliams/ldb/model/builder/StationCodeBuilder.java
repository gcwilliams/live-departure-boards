package uk.co.gcwilliams.ldb.model.builder;

import com.google.common.base.Strings;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Station;
import uk.co.gcwilliams.ldb.model.StationCode;

/**
 * The station code builder
 *
 * @author Gareth Williams (466567)
 */
public class StationCodeBuilder implements Builder<StationCode> {

    private String name;

    private Id<Station> stationId;

    public StationCodeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StationCodeBuilder setStationId(String stationId) {
        this.stationId = new Id<Station>(stationId);
        return this;
    }

    @Override
    public StationCode build() {
        if (Strings.isNullOrEmpty(name)) {
            throw new IllegalStateException("A name must be provided");
        }
        if (stationId == null) {
            throw new IllegalStateException("A station ID must be provided");
        }

        return new StationCodeImpl(name, stationId);
    }

    private static class StationCodeImpl implements StationCode {

        private final String name;

        private final Id<Station> stationId;

        private StationCodeImpl(String name, Id<Station> stationId) {
            this.name = name;
            this.stationId = stationId;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Id<Station> getStationId() {
            return stationId;
        }
    }
}
