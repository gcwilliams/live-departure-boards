package uk.co.gcwilliams.feature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import uk.co.gcwilliams.serializers.StationBoardsJacksonModule;

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
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new StationBoardsJacksonModule());
        mapper.registerModule(new JodaModule());
        jacksonJaxbJsonProvider.setMapper(mapper);
    }

    @Override
    public boolean configure(FeatureContext context) {
        context.register(jacksonJaxbJsonProvider, MessageBodyReader.class, MessageBodyWriter.class);
        return true;
    }
}
