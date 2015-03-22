package uk.co.gcwilliams.ldb.model.builder;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.joda.time.DateTime;
import uk.co.gcwilliams.ldb.model.Destination;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.Station;

/**
 * The service builder
 *
 * @author Gareth Williams (466567)
 */
public class ServiceBuilder extends AbstractServiceBuilder implements Builder<Service> {

    private StationBuilder origin;

    private DestinationBuilder destination;

    private String serviceId;

    /**
     * Sets the origin
     *
     * @param origin The origin
     * @return The service builder
     */
    public ServiceBuilder setOrigin(StationBuilder origin) {
        this.origin = origin;
        return this;
    }

    /**
     * Sets the destination
     *
     * @param destination The destination
     * @return The service builder
     */
    public ServiceBuilder setDestination(DestinationBuilder destination) {
        this.destination = destination;
        return this;
    }

    /**
     * Sets the standard time of arrival
     *
     * @param standardTimeOfArrival The standard time of arrival
     * @return The service builder
     */
    public ServiceBuilder setStandardTimeOfArrival(DateTime standardTimeOfArrival) {
        return (ServiceBuilder) super.setStandardTimeOfArrival(standardTimeOfArrival);
    }

    /**
     * Sets the estimated time of arrival
     *
     * @param estimatedTimeOfArrival The estimated time of arrival
     * @return The service builder
     */
    public ServiceBuilder setEstimatedTimeOfArrival(DateTime estimatedTimeOfArrival) {
        return (ServiceBuilder) super.setEstimatedTimeOfArrival(estimatedTimeOfArrival);
    }

    /**
     * Sets the standard time of departure
     *
     * @param standardTimeOfDeparture The standard time of departure
     * @return The service builder
     */
    public ServiceBuilder setStandardTimeOfDeparture(DateTime standardTimeOfDeparture) {
        return (ServiceBuilder) super.setStandardTimeOfDeparture(standardTimeOfDeparture);
    }

    /**
     * Sets the estimated time of departure
     *
     * @param estimatedTimeOfDeparture The estimated time of departure
     * @return The service builder
     */
    public ServiceBuilder setEstimatedTimeOfDeparture(DateTime estimatedTimeOfDeparture) {
        return (ServiceBuilder) super.setEstimatedTimeOfDeparture(estimatedTimeOfDeparture);
    }

    /**
     * Sets the platform
     *
     * @param platform The platform
     * @return The service builder
     */
    public ServiceBuilder setPlatform(Integer platform) {
        return (ServiceBuilder) super.setPlatform(platform);
    }

    /**
     * Sets the operator
     *
     * @param operator The operator
     * @return The service builder
     */
    public ServiceBuilder setOperator(String operator) {
        return (ServiceBuilder) super.setOperator(operator);
    }

    /**
     * Sets the operator code
     *
     * @param operatorCode The operator code
     * @return The service builder
     */
    public ServiceBuilder setOperatorCode(String operatorCode) {
        return (ServiceBuilder) super.setOperatorCode(operatorCode);
    }

    /**
     * Sets the service ID
     *
     * @param serviceId The service id
     * @return The service builder
     */
    public ServiceBuilder setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    @Override
    public Service build() {

        if (origin == null) {
            throw new IllegalStateException("An origin must be provided");
        }
        if (destination == null) {
            throw new IllegalStateException("An destination must be provided");
        }
        if (Strings.isNullOrEmpty(operator)) {
            throw new IllegalStateException("An operator must be provided");
        }
        if (Strings.isNullOrEmpty(operatorCode)) {
            throw new IllegalStateException("An operator code must be provided");
        }
        if (Strings.isNullOrEmpty(serviceId)) {
            throw new IllegalStateException("An service ID must be provided");
        }

        return new ServiceImpl(
            origin.build(),
            destination.build(),
            Optional.fromNullable(standardTimeOfArrival),
            Optional.fromNullable(estimatedTimeOfArrival),
            Optional.fromNullable(standardTimeOfDeparture),
            Optional.fromNullable(estimatedTimeOfDeparture),
            Optional.fromNullable(platform),
            operator,
            operatorCode,
            new Id<Service>(serviceId)
        );
    }

    private static class ServiceImpl implements Service {

        private final Station origin;

        private final Destination destination;

        private final Optional<DateTime> standardTimeOfArrival;

        private final Optional<DateTime> estimatedTimeOfArrival;

        private final Optional<DateTime> standardTimeOfDeparture;

        private final Optional<DateTime> estimatedTimeOfDeparture;

        private final Optional<Integer> platform;

        private final String operator;

        private final String operatorCode;

        private final Id<Service> serviceId;

        private ServiceImpl(
                Station origin,
                Destination destination,
                Optional<DateTime> standardTimeOfArrival,
                Optional<DateTime> estimatedTimeOfArrival,
                Optional<DateTime> standardTimeOfDeparture,
                Optional<DateTime> estimatedTimeOfDeparture,
                Optional<Integer> platform,
                String operator,
                String operatorCode,
                Id<Service> serviceId) {

            this.origin = origin;
            this.destination = destination;
            this.standardTimeOfArrival = standardTimeOfArrival;
            this.estimatedTimeOfArrival = estimatedTimeOfArrival;
            this.standardTimeOfDeparture = standardTimeOfDeparture;
            this.estimatedTimeOfDeparture = estimatedTimeOfDeparture;
            this.platform = platform;
            this.operator = operator;
            this.operatorCode = operatorCode;
            this.serviceId = serviceId;
        }

        @Override
        public Station getOrigin() {
            return origin;
        }

        @Override
        public Destination getDestination() {
            return destination;
        }

        @Override
        public Optional<DateTime> getStandardTimeOfArrival() {
            return standardTimeOfArrival;
        }

        @Override
        public Optional<DateTime> getEstimatedTimeOfArrival() {
            return estimatedTimeOfArrival;
        }

        @Override
        public Optional<DateTime> getStandardTimeOfDeparture() {
            return standardTimeOfDeparture;
        }

        @Override
        public Optional<DateTime> getEstimatedTimeOfDeparture() {
            return estimatedTimeOfDeparture;
        }

        @Override
        public Optional<Integer> getPlatform() {
            return platform;
        }

        @Override
        public String getOperator() {
            return operator;
        }

        @Override
        public String getOperatorCode() {
            return operatorCode;
        }

        @Override
        public Id<Service> getServiceId() {
            return serviceId;
        }
    }
}
