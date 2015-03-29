package uk.co.gcwilliams.ldb.model.builder;

import com.google.common.base.Strings;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Station;
import uk.co.gcwilliams.ldb.model.StationCode;

/**
 * The station code builder
 *
 * @author Gareth Williams
 */
public class StationCodeBuilder implements Builder<StationCode> {

    private String name;

    private String stationId;

    public StationCodeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StationCodeBuilder setStationId(String stationId) {
        this.stationId = stationId;
        return this;
    }

    @Override
    public StationCode build() {
        if (Strings.isNullOrEmpty(name)) {
            throw new IllegalStateException("A name must be provided");
        }
        if (Strings.isNullOrEmpty(stationId)) {
            throw new IllegalStateException("A station ID must be provided");
        }

        return new StationCodeImpl(name, new Id<Station>(stationId));
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

        @Override
        public int hashCode() {
            int hash = 1;
            hash = 31 * hash + (name == null ? 0 : name.hashCode());
            hash = 31 * hash + (stationId == null ? 0 : stationId.get().hashCode());
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || (!(obj instanceof StationCodeImpl))) {
                return false;
            }
            StationCodeImpl stationCodeImpl = (StationCodeImpl)obj;
            return name.equals(stationCodeImpl.name) && stationId.equals(stationCodeImpl.stationId);
        }
    }
}