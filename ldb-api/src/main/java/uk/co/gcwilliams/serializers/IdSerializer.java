package uk.co.gcwilliams.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
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
