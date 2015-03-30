package uk.co.gcwilliams.feature;


import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.glassfish.hk2.api.Factory;
import uk.co.gcwilliams.serializers.StationBoardsJacksonModule;

import javax.inject.Singleton;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

/**
 * The jackson jaxb json feature
 *
 * @author Gareth Williams
 */
public class LdbJacksonJaxbJsonFeature implements Feature {

    private final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

    /**
     * Default constructor
     *
     */
    public LdbJacksonJaxbJsonFeature() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationConfig.Feature.REQUIRE_SETTERS_FOR_GETTERS, false);
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new StationBoardsJacksonModule());
        jacksonJaxbJsonProvider.setMapper(mapper);
    }

    @Override
    public boolean configure(FeatureContext context) {
        context.register(jacksonJaxbJsonProvider, MessageBodyReader.class, MessageBodyWriter.class);
        return false;
    }
}
