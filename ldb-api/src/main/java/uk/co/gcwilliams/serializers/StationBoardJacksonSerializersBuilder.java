package uk.co.gcwilliams.serializers;

import com.google.common.collect.Maps;
import org.codehaus.jackson.map.BeanDescription;
import org.codehaus.jackson.map.BeanProperty;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.Serializers;
import org.codehaus.jackson.type.JavaType;

import java.util.Collections;
import java.util.Map;

/**
 * The station board serializers
 *
 * @author Gareth Williams (466567)
 */
public class StationBoardJacksonSerializersBuilder {

    private final Map<Class<?>, JsonSerializer<?>> serializers = Maps.newHashMap();

    /**
     * Adds a serializer to the serializers
     *
     * @param clazz the class the serializer serializes
     * @param serializer the serializer
     */
    public <T, S extends JsonSerializer<? extends T>> StationBoardJacksonSerializersBuilder addSerializer(Class<T> clazz, S serializer) {
        serializers.put(clazz, serializer);
        return this;
    }

    /**
     * Builds the serializers
     *
     * @return the serializers
     */
    public Serializers build() {
        return new StationBoardJacksonSerializers(serializers);
    }

    /**
     * The station board serializers
     *
     */
    private static class StationBoardJacksonSerializers extends Serializers.Base {

        private final Map<Class<?>, JsonSerializer<?>> serializers;

        /**
         * Default constructor
         *
         * @param serializers the serializers
         */
        private StationBoardJacksonSerializers(Map<Class<?>, JsonSerializer<?>> serializers) {
            this.serializers = Collections.unmodifiableMap(serializers);
        }

        @Override
        public JsonSerializer<?> findSerializer(
                SerializationConfig config,
                JavaType type,
                BeanDescription beanDesc,
                BeanProperty property) {
            return serializers.get(type.getRawClass());
        }
    }
}
