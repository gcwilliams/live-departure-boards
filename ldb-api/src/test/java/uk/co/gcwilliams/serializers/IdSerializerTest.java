package uk.co.gcwilliams.serializers;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import uk.co.gcwilliams.ldb.model.Id;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * The ID serializer tests
 *
 * Created by GWilliams on 25/03/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SerializerProvider.class)
public class IdSerializerTest {

    @Mock
    private JsonGenerator jGen;

    @Mock
    private SerializerProvider provider;

    @Test
    public void serializeId() throws IOException {

        // arrange
        Id<String> id = new Id<>("Simpson");
        IdSerializer serializer = new IdSerializer();

        // act
        serializer.serialize(id, jGen, provider);

        // assert
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(provider).defaultSerializeValue(captor.capture(), eq(jGen));
        assertThat(captor.getValue(), equalTo("Simpson"));
    }
}
