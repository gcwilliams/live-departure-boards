package uk.co.gcwilliams.ldb.model.builder;

import com.google.common.base.Optional;
import org.joda.time.DateTime;
import uk.co.gcwilliams.ldb.model.Station;
import uk.co.gcwilliams.ldb.model.SubsequentCallingPoint;

/**
 * The subsequent calling point builder
 *
 * @author Gareth Williams
 */
public class SubsequentCallingPointBuilder extends AbstractCallingPointBuilder implements Builder<SubsequentCallingPoint> {

    private DateTime estimatedDepartureTime;

    /**
     * Sets the station
     *
     * @param station The station
     * @return The subsequent calling point builder
     */
    public SubsequentCallingPointBuilder setStation(StationBuilder station) {
        return (SubsequentCallingPointBuilder) super.setStation(station);
    }

    /**
     * Sets the standard departure time
     *
     * @param standardDepartureTime The standard departure time
     * @return The subsequent calling point builder
     */
    public SubsequentCallingPointBuilder setStandardDepartureTime(DateTime standardDepartureTime) {
        return (SubsequentCallingPointBuilder) super.setStandardDepartureTime(standardDepartureTime);
    }

    /**
     * Sets the estimated departure time
     *
     * @param estimatedDepartureTime The estimated departure time
     * @return The subsequent calling point builder
     */
    public SubsequentCallingPointBuilder setEstimatedDepartureTime(DateTime estimatedDepartureTime) {
        this.estimatedDepartureTime = estimatedDepartureTime;
        return this;
    }

    @Override
    public SubsequentCallingPoint build() {
        if (station == null) {
            throw new IllegalStateException("A station must be provided");
        }
        return new SubsequentCallingPointImpl(
            station.build(),
            Optional.fromNullable(standardDepartureTime),
            Optional.fromNullable(estimatedDepartureTime)
        );
    }

    private class SubsequentCallingPointImpl implements SubsequentCallingPoint {

        private final Station station;

        private final Optional<DateTime> standardDepartureTime;

        private final Optional<DateTime> estimatedDepartureTime;

        private SubsequentCallingPointImpl(
                Station station,
                Optional<DateTime> standardDepartureTime,
                Optional<DateTime> estimatedDepartureTime) {
            this.station = station;
            this.standardDepartureTime = standardDepartureTime;
            this.estimatedDepartureTime = estimatedDepartureTime;
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
        public Optional<DateTime> getEstimatedDepartureTime() {
            return estimatedDepartureTime;
        }
    }
}
