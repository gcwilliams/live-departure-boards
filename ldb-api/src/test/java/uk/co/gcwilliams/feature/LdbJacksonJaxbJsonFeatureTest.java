package uk.co.gcwilliams.feature;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * The LDB jackson jaxb json feature tests
 *
 * Created by GWilliams on 30/03/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class LdbJacksonJaxbJsonFeatureTest {

    @Mock
    private FeatureContext featureContext;

    @Test
    public void testFeatureIsRegistered() {

        // arrange
        LdbJacksonJaxbJsonFeature feature = new LdbJacksonJaxbJsonFeature();

        // act
        feature.configure(featureContext);

        // assert
        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(featureContext).register(captor.capture(), eq(MessageBodyReader.class), eq(MessageBodyWriter.class));
        assertThat(captor.getValue(), instanceOf(JacksonJaxbJsonProvider.class));
    }

    @Test
    public void testFeatureIsRegisteredSuccessfully() {

        // arrange
        LdbJacksonJaxbJsonFeature feature = new LdbJacksonJaxbJsonFeature();

        // act
        boolean isRegistered = feature.configure(featureContext);

        // assert
        assertThat(isRegistered, equalTo(true));
    }
}
