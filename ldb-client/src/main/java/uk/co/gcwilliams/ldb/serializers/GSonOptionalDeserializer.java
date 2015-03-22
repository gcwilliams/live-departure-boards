package uk.co.gcwilliams.ldb.serializers;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * @author Gareth Williams (466567)
 */
public class GSonOptionalDeserializer implements JsonDeserializer<Optional> {

    @Override
    public Optional deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        String value = jsonElement.getAsString();
        return Strings.isNullOrEmpty(value) ? Optional.absent() : Optional.of(value);
    }
}
