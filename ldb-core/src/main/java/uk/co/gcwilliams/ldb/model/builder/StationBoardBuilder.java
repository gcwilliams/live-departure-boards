package uk.co.gcwilliams.ldb.model.builder;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.Station;
import uk.co.gcwilliams.ldb.model.StationBoard;

import java.util.Collections;
import java.util.List;

/**
 * The station board builder
 *
 * @author Gareth Williams (466567)
 */
public class StationBoardBuilder implements Builder<StationBoard> {

    private DateTime generatedAt;

    private StationBuilder station;

    private List<ServiceBuilder> services;

    /**
     * Sets the generated at date / time
     *
     * @param generatedAt The generated date / time
     * @return The station board builder
     */
    public StationBoardBuilder setGeneratedAt(DateTime generatedAt) {
        this.generatedAt = generatedAt;
        return this;
    }

    /**
     * Sets the station
     *
     * @param station The station
     * @return The station board builder
     */
    public StationBoardBuilder setStation(StationBuilder station) {
        this.station = station;
        return this;
    }

    /**
     * Sets the services
     *
     * @param services The services
     * @return The station board builder
     */
    public StationBoardBuilder setServices(List<ServiceBuilder> services) {
        this.services = services;
        return this;
    }

    @Override
    public StationBoard build() {
        if (generatedAt == null) {
            throw new IllegalStateException("A generated at date / time must be provided");
        }
        if (station == null) {
            throw new IllegalStateException("A station must be provided");
        }
        if (services == null) {
            throw new IllegalStateException("The services must be provided");
        }
        return new StationBoardImpl(generatedAt, station.build(), Iterables.transform(services, new BuilderTransformation<Service>()));
    }

    private static class StationBoardImpl implements StationBoard {

        private final DateTime generatedAt;

        private final Station station;

        private final List<Service> services;

        private StationBoardImpl(DateTime generatedAt, Station station, Iterable<Service> services) {
            this.generatedAt = generatedAt;
            this.station = station;
            this.services = Collections.unmodifiableList(Lists.newArrayList(services));
        }

        @Override
        public DateTime getGeneratedAt() {
            return generatedAt;
        }

        @Override
        public Station getStation() {
            return station;
        }

        @Override
        public List<Service> getServices() {
            return services;
        }
    }
}
