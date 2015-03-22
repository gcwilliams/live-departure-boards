package uk.co.gcwilliams.serializers;

import org.codehaus.jackson.map.BeanDescription;
import org.codehaus.jackson.map.BeanProperty;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.Serializers;
import org.codehaus.jackson.type.JavaType;

import java.util.Arrays;
import java.util.List;

/**
 * @author Gareth Williams (466567)
 */
public class StationBoardJacksonSerializers extends Serializers.Base {

    private final List<CanSerialize<?>> serializers;

    public StationBoardJacksonSerializers(CanSerialize<?>... serializers) {
        this.serializers = Arrays.asList(serializers);
    }

    @Override
    public JsonSerializer<?> findSerializer(
            SerializationConfig config,
            JavaType type,
            BeanDescription beanDesc,
            BeanProperty property) {

        for (CanSerialize<?> serializer : serializers) {
            if (!serializer.canSerialize(type)) {
                continue;
            }
            return serializer.getSerializer();
        }
        return super.findSerializer(config, type, beanDesc, property);
    }
}
