package uk.co.gcwilliams.serializers;

import com.google.common.base.Optional;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * The optional serializer tests
 *
 * Created by GWilliams on 25/03/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SerializerProvider.class)
public class OptionalSerializerTest {

    @Mock
    private JsonGenerator jGen;

    @Mock
    private SerializerProvider provider;

    @Test
    public void serializeAbsent() throws IOException {

        // arrange
        Optional<?> value = Optional.absent();
        OptionalSerializer serializer = new OptionalSerializer();

        // act
        serializer.serialize(value, jGen, provider);

        // assert
        verify(provider).defaultSerializeNull(eq(jGen));
    }

    @Test
    public void serializePresent() throws IOException {

        // arrange
        Optional<?> value = Optional.of("Homer");
        OptionalSerializer serializer = new OptionalSerializer();

        // act
        serializer.serialize(value, jGen, provider);

        // assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(provider).defaultSerializeValue(captor.capture(), eq(jGen));
        assertThat(captor.getValue(), equalTo("Homer"));
    }
}
