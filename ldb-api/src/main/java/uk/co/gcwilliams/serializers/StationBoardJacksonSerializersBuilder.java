package uk.co.gcwilliams.serializers;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

/**
 * The station board serializers
 *
 * @author Gareth Williams
 */
public class StationBoardJacksonSerializersBuilder {

    private final Map<Class<?>, JsonSerializer<?>> serializers = Maps.newHashMap();

    /**
     * Adds a serializer to the serializers
     *
     * @param clazz the class the serializer serializes
     * @param serializer the serializer
     */
    public <T, S extends JsonSerializer<? extends T>> StationBoardJacksonSerializersBuilder addSerializer(
            Class<T> clazz,
            S serializer) {
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
                BeanDescription beanDesc) {
            return serializers.entrySet()
                .stream()
                .filter(e -> e.getKey().isAssignableFrom(type.getRawClass()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
        }
    }
}
