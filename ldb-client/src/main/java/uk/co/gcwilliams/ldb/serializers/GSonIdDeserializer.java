package uk.co.gcwilliams.ldb.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.joda.time.DateTime;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;

import java.lang.reflect.Type;

/**
 * @author Gareth Williams (466567)
 */
public class GSonIdDeserializer<T> implements JsonDeserializer<Id<T>> {
    @Override
    public Id<T> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        return new Id<T>(jsonElement.getAsString());
    }
}
