package uk.co.gcwilliams;

import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import uk.co.gcwilliams.factory.LdbSoapServiceFactory;
import uk.co.gcwilliams.factory.LuceneDirectoryReaderFactory;
import uk.co.gcwilliams.factory.StationBoardsFactory;
import uk.co.gcwilliams.factory.StationCodeReaderFactory;
import uk.co.gcwilliams.feature.LdbJacksonJaxbJsonFeature;
import uk.co.gcwilliams.injection.ReflectiveBinder;
import uk.co.gcwilliams.properties.LdbKeyProperty;
import uk.co.gcwilliams.properties.StationCodesProperty;
import uk.co.gcwilliams.service.StationCodesServiceImpl;

import javax.ws.rs.ApplicationPath;
import java.util.stream.Stream;

import static java.util.stream.Stream.of;

/**
 * The LDB application
 *
 * @author Gareth Williams
 */
@ApplicationPath("api")
public class LdbApplication extends ResourceConfig {

    /**
     * Default constructor
     *
     */
    public LdbApplication() {
        packages("uk.co.gcwilliams.api");
        register(RolesAllowedDynamicFeature.class);
        register(LdbJacksonJaxbJsonFeature.class);
        register(new ReflectiveBinder() {

            @Override
            protected Stream<Class<?>> bind() {
                return of(
                    StationCodesServiceImpl.class,
                    StationCodesProperty.class,
                    LdbKeyProperty.class
                );
            }

            @Override
            protected Stream<Class<? extends Factory>> bindFactories() {
                return of(
                    LdbSoapServiceFactory.class,
                    LuceneDirectoryReaderFactory.class,
                    StationBoardsFactory.class,
                    StationCodeReaderFactory.class
                );
            }
        });
    }
}