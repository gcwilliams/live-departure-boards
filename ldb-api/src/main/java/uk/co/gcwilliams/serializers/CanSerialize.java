package uk.co.gcwilliams.serializers;

import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.type.JavaType;

/**
 * @author Gareth Williams (466567)
 */
public interface CanSerialize<T> {

    boolean canSerialize(JavaType type);

    JsonSerializer<T> getSerializer();
}
