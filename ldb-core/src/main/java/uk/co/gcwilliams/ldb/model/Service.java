package uk.co.gcwilliams.ldb.model;

/**
 * The service interface, represents a train service
 *
 * @author Gareth Williams (466567)
 */
public interface Service extends ServiceInformation {

    /**
     * Gets the origin
     *
     * @return The origin
     */
    Station getOrigin();

    /**
     * Gets the destination
     *
     * @return The destination
     */
    Destination getDestination();

    /**
     * Gets the service ID
     *
     * @return The service ID
     */
    Id<Service> getServiceId();
}
