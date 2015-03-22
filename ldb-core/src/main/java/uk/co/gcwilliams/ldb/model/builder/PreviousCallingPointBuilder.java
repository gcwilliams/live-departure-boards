package uk.co.gcwilliams.ldb.model.builder;

import com.google.common.base.Optional;
import org.joda.time.DateTime;
import uk.co.gcwilliams.ldb.model.PreviousCallingPoint;
import uk.co.gcwilliams.ldb.model.Station;

/**
 * The previous calling point builder
 *
 * @author Gareth Williams (466567)
 */
public class PreviousCallingPointBuilder extends AbstractCallingPointBuilder implements Builder<PreviousCallingPoint> {

    private DateTime actualDepartureTime;

    /**
     * Sets the station
     *
     * @param station The station
     * @return The previous calling point builder
     */
    public PreviousCallingPointBuilder setStation(StationBuilder station) {
        return (PreviousCallingPointBuilder) super.setStation(station);
    }

    /**
     * Sets the standard departure time
     *
     * @param standardDepartureTime The standard departure time
     * @return The previous calling point builder
     */
    public PreviousCallingPointBuilder setStandardDepartureTime(DateTime standardDepartureTime) {
        return (PreviousCallingPointBuilder) super.setStandardDepartureTime(standardDepartureTime);
    }

    /**
     * Sets the actual departure time
     *
     * @param actualDepartureTime The actual departure time
     * @return The previous calling point builder
     */
    public PreviousCallingPointBuilder setActualDepartureTime(DateTime actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
        return this;
    }

    @Override
    public PreviousCallingPoint build() {
        if (station == null) {
            throw new IllegalStateException("A station must be provided");
        }
        if (standardDepartureTime == null) {
            throw new IllegalStateException("A standard departure time must be provided");
        }
        return new PreviousCallingPointImpl(
            station.build(),
            Optional.fromNullable(standardDepartureTime),
            Optional.fromNullable(actualDepartureTime)
        );
    }

    private static class PreviousCallingPointImpl implements PreviousCallingPoint {

        private final Station station;

        private final Optional<DateTime> standardDepartureTime;

        private final Optional<DateTime> actualDepartureTime;

        private PreviousCallingPointImpl(
                Station station,
                Optional<DateTime> standardDepartureTime,
                Optional<DateTime> actualDepartureTime) {

            this.station = station;
            this.standardDepartureTime = standardDepartureTime;
            this.actualDepartureTime = actualDepartureTime;
        }

        @Override
        public Station getStation() {
            return station;
        }

        @Override
        public Optional<DateTime> getStandardDepartureTime() {
            return standardDepartureTime;
        }

        @Override
        public Optional<DateTime> getActualDepartureTime() {
            return actualDepartureTime;
        }
    }
}
