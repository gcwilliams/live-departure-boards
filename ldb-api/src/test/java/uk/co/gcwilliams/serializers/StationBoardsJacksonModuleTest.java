package uk.co.gcwilliams.serializers;

import org.codehaus.jackson.map.Module;
import org.codehaus.jackson.map.Serializers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

/**
 * The station boards jackson module tests
 *
 * Created by GWilliams on 25/03/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class StationBoardsJacksonModuleTest {

    @Mock
    private Module.SetupContext context;

    @Test
    public void hasSerializerForDateTime() {

        // arrange
        StationBoardsJacksonModule module = new StationBoardsJacksonModule();

        // act
        module.setupModule(context);

        // assert
        ArgumentCaptor<Serializers> serializersCaptor = ArgumentCaptor.forClass(Serializers.class);
        verify(context).addSerializers(serializersCaptor.capture());
        assertThat(serializersCaptor.getValue(), instanceOf(Serializers.class));
    }
}
