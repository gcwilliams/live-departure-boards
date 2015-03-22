package uk.co.gcwilliams.serializers;

import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.type.JavaType;

/**
 * @author Gareth Williams (466567)
 */
public interface CanSerialize<T> {

    /**
     * Determines if the serializer can serializer type
     *
     * @param type the type
     * @return true if the serializer can serialize the type, false otherwise
     */
    boolean canSerialize(JavaType type);

    /**
     * Gets the serializer
     *
     * @return the serializer
     */
    JsonSerializer<T> getSerializer();
}
