package uk.co.gcwilliams.serializers;

import com.google.common.base.Optional;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;

/**
 * The optional getSerializer
 *
 * @author Gareth Williams (466567)
 */
public class OptionalSerializer extends JsonSerializer<Optional<?>> implements CanSerialize<Optional<?>> {

    @Override
    public void serialize(Optional<?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        if (value.isPresent()) {
            provider.defaultSerializeValue(value.get(), jgen);
        } else {
            provider.defaultSerializeNull(jgen);
        }
    }

    @Override
    public boolean canSerialize(JavaType type) {
        return Optional.class.isAssignableFrom(type.getRawClass());
    }

    @Override
    public JsonSerializer<Optional<?>> getSerializer() {
        return this;
    }
}
