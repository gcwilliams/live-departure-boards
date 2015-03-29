package uk.co.gcwilliams.ldb.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

/**
 * A date time deserializer
 *
 * @author Gareth Williams
 */
public class GSonDateTimeDeserializer implements JsonDeserializer<DateTime> {

    private static final DateTimeFormatter ISO_FORMATTER = ISODateTimeFormat.dateTime();

    @Override
    public DateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        return ISO_FORMATTER.parseDateTime(jsonElement.getAsString());
    }
}
