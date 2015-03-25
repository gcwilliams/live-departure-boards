package uk.co.gcwilliams.serializers;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * The date time serializer tests
 *
 * Created by GWilliams on 25/03/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SerializerProvider.class)
public class DateTimeSerializerTest {

    @Mock
    private JsonGenerator jGen;

    @Mock
    private SerializerProvider provider;

    @Test
    public void serializeDateTime() throws IOException {

        // arrange
        DateTime value = DateTime.parse("2015-03-25T18:14:11.000Z");
        DateTimeSerializer serializer = new DateTimeSerializer();

        // act
        serializer.serialize(value, jGen, provider);

        // assert
        ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);
        verify(provider).defaultSerializeValue(valueCaptor.capture(), eq(jGen));
        assertThat(valueCaptor.getValue(), equalTo("2015-03-25T18:14:11.000Z"));
    }
}
