package uk.co.gcwilliams.serializers;

import com.google.common.base.Optional;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * The optional serializer
 *
 * @author Gareth Williams (466567)
 */
public class OptionalSerializer extends JsonSerializer<Optional<?>> {

    @Override
    public void serialize(Optional<?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        if (value.isPresent()) {
            provider.defaultSerializeValue(value.get(), jgen);
        } else {
            provider.defaultSerializeNull(jgen);
        }
    }
}
