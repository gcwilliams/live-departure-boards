package uk.co.gcwilliams.serializers;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.type.JavaType;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * @author Gareth Williams (466567)
 */
public class DateTimeSerializer extends JsonSerializer<DateTime> implements CanSerialize<DateTime> {

    private final DateTimeFormatter ISO_FORMATTER = ISODateTimeFormat.dateTime().withZoneUTC();

    @Override
    public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        provider.defaultSerializeValue(ISO_FORMATTER.print(value), jgen);
    }

    @Override
    public boolean canSerialize(JavaType type) {
        return DateTime.class.isAssignableFrom(type.getRawClass());
    }

    @Override
    public JsonSerializer<DateTime> getSerializer() {
        return this;
    }
}
