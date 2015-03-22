package uk.co.gcwilliams;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import uk.co.gcwilliams.codes.StationCodes;
import uk.co.gcwilliams.factory.StationBoardsFactory;
import uk.co.gcwilliams.factory.StationCodesFactory;
import uk.co.gcwilliams.ldb.service.StationBoards;
import uk.co.gcwilliams.providers.ObjectMapperProvider;

import javax.ws.rs.ApplicationPath;

/**
 * The LDB application
 *
 * @author Gareth Williams (466567)
 */
@ApplicationPath("api")
public class LdbApplication extends ResourceConfig {

    /**
     * Default constructor
     *
     */
    public LdbApplication() {
        packages("uk.co.gcwilliams.api");
        register(JacksonFeature.class);
        register(ObjectMapperProvider.class);
        register(RolesAllowedDynamicFeature.class);

        registerInstances(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(StationCodesFactory.class).to(StationCodes.class);
                bindFactory(StationBoardsFactory.class).to(StationBoards.class);
            }
        });
    }
}
