package uk.co.gcwilliams.serializers;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.type.JavaType;
import uk.co.gcwilliams.ldb.model.Id;

import java.io.IOException;

/**
 * The ID serializer
 *
 * @author Gareth Williams
 */
public class IdSerializer extends JsonSerializer<Id<?>> {

    @Override
    public void serialize(Id<?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        provider.defaultSerializeValue(value.get(), jgen);
    }
}
