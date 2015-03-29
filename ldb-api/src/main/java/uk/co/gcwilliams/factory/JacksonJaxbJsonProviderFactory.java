package uk.co.gcwilliams.factory;


import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.glassfish.hk2.api.Factory;
import uk.co.gcwilliams.serializers.StationBoardsJacksonModule;

import javax.inject.Singleton;

/**
 * The object mapper provider
 *
 * @author Gareth Williams
 */
public class JacksonJaxbJsonProviderFactory implements Factory<JacksonJaxbJsonProvider> {

    private final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();

    /**
     * Default constructor
     *
     */
    public JacksonJaxbJsonProviderFactory() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationConfig.Feature.REQUIRE_SETTERS_FOR_GETTERS, false);
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new StationBoardsJacksonModule());
        jacksonJaxbJsonProvider.setMapper(mapper);
    }

    @Override
    @Singleton
    public JacksonJaxbJsonProvider provide() {
        return jacksonJaxbJsonProvider;
    }

    @Override
    public void dispose(JacksonJaxbJsonProvider jacksonJaxbJsonProvider) { }
}
