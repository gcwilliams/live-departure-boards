package uk.co.gcwilliams.ldb.model.builder;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import uk.co.gcwilliams.ldb.model.CallingPoint;
import uk.co.gcwilliams.ldb.model.PreviousCallingPoint;
import uk.co.gcwilliams.ldb.model.ServiceDetail;
import uk.co.gcwilliams.ldb.model.Station;
import uk.co.gcwilliams.ldb.model.SubsequentCallingPoint;

import java.util.Collections;
import java.util.List;

/**
 * The service detail builder
 *
 * @author Gareth Williams (466567)
 */
public class ServiceDetailBuilder extends AbstractServiceBuilder implements Builder<ServiceDetail> {

    private DateTime generatedAt;

    private StationBuilder currentStation;

    private List<List<PreviousCallingPointBuilder>> previousCallingPoints = Lists.newArrayList();

    private List<List<SubsequentCallingPointBuilder>> subsequentCallingPoints = Lists.newArrayList();

    /**
     * Sets the generated at date / time
     *
     * @param generatedAt The generated date / time
     * @return The service detail builder
     */
    public ServiceDetailBuilder setGeneratedAt(DateTime generatedAt) {
        this.generatedAt = generatedAt;
        return this;
    }

    /**
     * Sets the current station
     *
     * @param currentStation The current station
     * @return The service detail builder
     */
    public ServiceDetailBuilder setCurrentStation(StationBuilder currentStation) {
        this.currentStation = currentStation;
        return this;
    }

    /**
     * Adds the previous calling points
     *
     * @param previousCallingPoints The previous calling points
     * @return The service detail builder
     */
    public ServiceDetailBuilder addPreviousCallingPoints(List<PreviousCallingPointBuilder> previousCallingPoints) {
        this.previousCallingPoints.add(previousCallingPoints);
        return this;
    }

    /**
     * Sets the previous calling points
     *
     * @param previousCallingPoints The previous calling points
     * @return The service detail builder
     */
    public ServiceDetailBuilder setPreviousCallingPoints(List<List<PreviousCallingPointBuilder>> previousCallingPoints) {
        this.previousCallingPoints = previousCallingPoints;
        return this;
    }

    /**
     * Adds the subsequent calling points
     *
     * @param subsequentCallingPoints The subsequent calling points
     * @return The service detail builder
     */
    public ServiceDetailBuilder addSubsequentCallingPoints(List<SubsequentCallingPointBuilder> subsequentCallingPoints) {
        this.subsequentCallingPoints.add(subsequentCallingPoints);
        return this;
    }

    /**
     * Adds the subsequent calling points
     *
     * @param subsequentCallingPoints The subsequent calling points
     * @return The service detail builder
     */
    public ServiceDetailBuilder setSubsequentCallingPoints(List<List<SubsequentCallingPointBuilder>> subsequentCallingPoints) {
        this.subsequentCallingPoints = subsequentCallingPoints;
        return this;
    }

    /**
     * Sets the standard time of arrival
     *
     * @param standardTimeOfArrival The standard time of arrival
     * @return The service detail builder
     */
    public ServiceDetailBuilder setStandardTimeOfArrival(DateTime standardTimeOfArrival) {
        return (ServiceDetailBuilder) super.setStandardTimeOfArrival(standardTimeOfArrival);
    }

    /**
     * Sets the estimated time of arrival
     *
     * @param estimatedTimeOfArrival The estimated time of arrival
     * @return The service detail builder
     */
    public ServiceDetailBuilder setEstimatedTimeOfArrival(DateTime estimatedTimeOfArrival) {
        return (ServiceDetailBuilder) super.setEstimatedTimeOfArrival(estimatedTimeOfArrival);
    }

    /**
     * Sets the standard time of departure
     *
     * @param standardTimeOfDeparture The standard time of departure
     * @return The service detail builder
     */
    public ServiceDetailBuilder setStandardTimeOfDeparture(DateTime standardTimeOfDeparture) {
        return (ServiceDetailBuilder) super.setStandardTimeOfDeparture(standardTimeOfDeparture);
    }

    /**
     * Sets the estimated time of departure
     *
     * @param estimatedTimeOfDeparture The estimated time of departure
     * @return The service detail builder
     */
    public ServiceDetailBuilder setEstimatedTimeOfDeparture(DateTime estimatedTimeOfDeparture) {
        return (ServiceDetailBuilder) super.setEstimatedTimeOfDeparture(estimatedTimeOfDeparture);
    }

    /**
     * Sets the platform
     *
     * @param platform The platform
     * @return The service detail builder
     */
    public ServiceDetailBuilder setPlatform(Integer platform) {
        return (ServiceDetailBuilder) super.setPlatform(platform);
    }

    /**
     * Sets the operator
     *
     * @param operator The operator
     * @return The service detail builder
     */
    public ServiceDetailBuilder setOperator(String operator) {
        return (ServiceDetailBuilder) super.setOperator(operator);
    }

    /**
     * Sets the operator code
     *
     * @param operatorCode The operator code
     * @return The service detail builder
     */
    public ServiceDetailBuilder setOperatorCode(String operatorCode) {
        return (ServiceDetailBuilder) super.setOperatorCode(operatorCode);
    }

    @Override
    public ServiceDetail build() {
        if (generatedAt == null) {
            throw new IllegalStateException("A generated at date / time must be provided");
        }
        if (currentStation == null) {
            throw new IllegalStateException("A current station must be provided");
        }
        if (Strings.isNullOrEmpty(operator)) {
            throw new IllegalStateException("An operator must be provided");
        }
        if (Strings.isNullOrEmpty(operatorCode)) {
            throw new IllegalStateException("An operator code must be provided");
        }
        if (Iterables.size(Iterables.concat(previousCallingPoints)) == 0) {
            throw new IllegalStateException("An previous calling points must be provided");
        }
        if (Iterables.size(Iterables.concat(subsequentCallingPoints)) == 0) {
            throw new IllegalStateException("An subsequent calling points must be provided");
        }
        return new ServiceDetailImpl(
            generatedAt,
            currentStation.build(),
            transformCallingPoints(previousCallingPoints),
            transformCallingPoints(subsequentCallingPoints),
            Optional.fromNullable(standardTimeOfArrival),
            Optional.fromNullable(estimatedTimeOfArrival),
            Optional.fromNullable(standardTimeOfDeparture),
            Optional.fromNullable(estimatedTimeOfDeparture),
            Optional.fromNullable(platform),
            operator,
            operatorCode
        );
    }

    private static class ServiceDetailImpl implements ServiceDetail {

        private final DateTime generatedAt;

        private final Station currentStation;

        private final List<List<PreviousCallingPoint>> previousCallingPoints;

        private final List<List<SubsequentCallingPoint>> subsequentCallingPoints;

        private final Optional<DateTime> standardTimeOfArrival;

        private final Optional<DateTime> estimatedTimeOfArrival;

        private final Optional<DateTime>  standardTimeOfDeparture;

        private final Optional<DateTime> estimatedTimeOfDeparture;

        private final Optional<Integer> platform;

        private final String operator;

        private final String operatorCode;

        private ServiceDetailImpl(
                DateTime generatedAt,
                Station currentStation,
                List<List<PreviousCallingPoint>> previousCallingPoints,
                List<List<SubsequentCallingPoint>> subsequentCallingPoints,
                Optional<DateTime> standardTimeOfArrival,
                Optional<DateTime> estimatedTimeOfArrival,
                Optional<DateTime> standardTimeOfDeparture,
                Optional<DateTime> estimatedTimeOfDeparture,
                Optional<Integer> platform,
                String operator,
                String operatorCode) {

            this.generatedAt = generatedAt;
            this.currentStation = currentStation;
            this.previousCallingPoints = previousCallingPoints;
            this.subsequentCallingPoints = subsequentCallingPoints;
            this.standardTimeOfArrival = standardTimeOfArrival;
            this.estimatedTimeOfArrival = estimatedTimeOfArrival;
            this.standardTimeOfDeparture = standardTimeOfDeparture;
            this.estimatedTimeOfDeparture = estimatedTimeOfDeparture;
            this.platform = platform;
            this.operator = operator;
            this.operatorCode = operatorCode;
        }

        @Override
        public DateTime getGeneratedAt() {
            return generatedAt;
        }

        @Override
        public Station getCurrentStation() {
            return currentStation;
        }

        @Override
        public List<List<PreviousCallingPoint>> getPreviousCallingPoints() {
            return previousCallingPoints;
        }

        @Override
        public List<List<SubsequentCallingPoint>> getSubsequentCallingPoints() {
            return subsequentCallingPoints;
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
    }

    /**
     * Helper method to transform a List<List<TBuilder>>> to an immutable List<List<T>>
     *
     * @param callingPoints The calling points
     * @return The transformed calling points
     */
    private static <TC extends CallingPoint, TB extends Builder<TC>> List<List<TC>> transformCallingPoints(Iterable<List<TB>> callingPoints) {

        Iterable<List<TC>> transformed = Iterables.transform(
                callingPoints,
                new Function<List<TB>, List<TC>>() {
                    @Override
                    public List<TC> apply(List<TB> input) {
                        return Collections.unmodifiableList(Lists.newArrayList(Iterables.transform(input, new BuilderTransformation<TC>())));
                    }
                });

        return Collections.unmodifiableList(Lists.newArrayList(transformed));
    }
}
