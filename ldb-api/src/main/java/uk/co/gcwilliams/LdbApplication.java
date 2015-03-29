package uk.co.gcwilliams;

import org.apache.lucene.index.IndexReader;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import uk.co.gcwilliams.factory.LdbSoapServiceFactory;
import uk.co.gcwilliams.factory.LuceneDirectoryReaderFactory;
import uk.co.gcwilliams.factory.JacksonJaxbJsonProviderFactory;
import uk.co.gcwilliams.factory.StationBoardsFactory;
import uk.co.gcwilliams.factory.StationCodeReaderFactory;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.service.StationBoards;
import uk.co.gcwilliams.ldb.stubs.LDBServiceSoap;
import uk.co.gcwilliams.properties.LdbKey;
import uk.co.gcwilliams.properties.LdbKeyProperty;
import uk.co.gcwilliams.properties.Property;
import uk.co.gcwilliams.properties.StationCodes;
import uk.co.gcwilliams.properties.StationCodesProperty;
import uk.co.gcwilliams.service.StationCodesService;
import uk.co.gcwilliams.service.StationCodesServiceImpl;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.util.List;

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
        register(StationCodesServiceImpl.class, StationCodesService.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(StationCodesProperty.class)
                    .qualifiedBy(StationCodesProperty.class.getAnnotation(StationCodes.class))
                    .to(Property.class);
                bind(LdbKeyProperty.class)
                    .qualifiedBy(LdbKeyProperty.class.getAnnotation(LdbKey.class))
                    .to(Property.class);
                bindFactory(LdbSoapServiceFactory.class).to(LDBServiceSoap.class);
                bindFactory(LuceneDirectoryReaderFactory.class).to(IndexReader.class);
                bindFactory(JacksonJaxbJsonProviderFactory.class).to(MessageBodyReader.class).to(MessageBodyWriter.class);
                bindFactory(StationBoardsFactory.class).to(StationBoards.class);
                bindFactory(StationCodeReaderFactory.class).to(new TypeLiteral<List<StationCode>>() { });
            }
        });
    }
}