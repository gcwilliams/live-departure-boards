package uk.co.gcwilliams.serializers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.base.Optional;

import java.io.IOException;

/**
 * The optional serializer
 *
 * @author Gareth Williams
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
