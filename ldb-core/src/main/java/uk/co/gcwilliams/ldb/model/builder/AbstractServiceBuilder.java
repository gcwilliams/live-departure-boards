package uk.co.gcwilliams.ldb.model.builder;

import org.joda.time.DateTime;

/**
 * The abstract service builder, contains the common fields between service
 * and service detail
 *
 * @author Gareth Williams
 */
public abstract class AbstractServiceBuilder {

    protected DateTime standardTimeOfArrival;

    protected DateTime estimatedTimeOfArrival;

    protected Integer platform;

    protected DateTime standardTimeOfDeparture;

    protected DateTime estimatedTimeOfDeparture;

    protected String operator;

    protected String operatorCode;

    /**
     * Sets the standard time of arrival
     *
     * @param standardTimeOfArrival The standard time of arrival
     * @return The service builder
     */
    public AbstractServiceBuilder setStandardTimeOfArrival(DateTime standardTimeOfArrival) {
        this.standardTimeOfArrival = standardTimeOfArrival;
        return this;
    }

    /**
     * Sets the estimated time of arrival
     *
     * @param estimatedTimeOfArrival The estimated time of arrival
     * @return The service builder
     */
    public AbstractServiceBuilder setEstimatedTimeOfArrival(DateTime estimatedTimeOfArrival) {
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
        return this;
    }

    /**
     * Sets the standard time of departure
     *
     * @param standardTimeOfDeparture The standard time of departure
     * @return The service builder
     */
    public AbstractServiceBuilder setStandardTimeOfDeparture(DateTime standardTimeOfDeparture) {
        this.standardTimeOfDeparture = standardTimeOfDeparture;
        return this;
    }

    /**
     * Sets the estimated time of departure
     *
     * @param estimatedTimeOfDeparture The estimated time of departure
     * @return The service builder
     */
    public AbstractServiceBuilder setEstimatedTimeOfDeparture(DateTime estimatedTimeOfDeparture) {
        this.estimatedTimeOfDeparture = estimatedTimeOfDeparture;
        return this;
    }

    /**
     * Sets the platform
     *
     * @param platform The platform
     * @return The service builder
     */
    public AbstractServiceBuilder setPlatform(Integer platform) {
        this.platform = platform;
        return this;
    }

    /**
     * Sets the operator
     *
     * @param operator The operator
     * @return The service builder
     */
    public AbstractServiceBuilder setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    /**
     * Sets the operator code
     *
     * @param operatorCode The operator code
     * @return The service builder
     */
    public AbstractServiceBuilder setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
        return this;
    }
}
