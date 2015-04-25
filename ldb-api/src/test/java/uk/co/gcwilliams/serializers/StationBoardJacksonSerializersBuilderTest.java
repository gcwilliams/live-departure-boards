package uk.co.gcwilliams.serializers;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import uk.co.gcwilliams.ldb.model.Id;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * The station board jackson serializer builder tests
 *
 * Created by GWilliams on 25/03/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JavaType.class, SerializationConfig.class })
public class StationBoardJacksonSerializersBuilderTest {

    @Mock
    private SerializationConfig config;

    @Mock
    private JavaType type;

    @Mock
    private BeanDescription beanDesc;

    @Test
    @SuppressWarnings("unchecked")
    public void testFindSerializerWhenItExists() {

        // arrange
        when(type.getRawClass()).thenReturn((Class) Id.class);

        Serializers serializers = new StationBoardJacksonSerializersBuilder()
                .addSerializer(Id.class, new IdSerializer())
                .build();

        // act
        JsonSerializer<?> serializer = serializers.findSerializer(config, type, beanDesc);

        // assert
        assertThat(serializer, instanceOf(IdSerializer.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFindSerializerWhenItDoesNotExist() {

        // arrange
        when(type.getRawClass()).thenReturn((Class) Optional.class);

        Serializers serializers = new StationBoardJacksonSerializersBuilder()
                .addSerializer(Id.class, new IdSerializer())
                .build();

        // act
        JsonSerializer<?> serializer = serializers.findSerializer(config, type, beanDesc);

        // assert
        assertThat(serializer, nullValue());
    }
}