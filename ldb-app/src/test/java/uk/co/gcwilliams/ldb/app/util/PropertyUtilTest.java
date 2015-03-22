package uk.co.gcwilliams.ldb.app.util;

import android.content.res.AssetManager;
import com.google.common.base.Charsets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import uk.co.gcwilliams.ldb.app.LdbConstants;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * The property util tests
 *
 * Created by GWilliams on 22/03/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(AssetManager.class)
public class PropertyUtilTest {

    @Test
    public void testPropertiesLoad() throws IOException {

        // arrange
        AssetManager manager = PowerMockito.mock(AssetManager.class);
        byte[] propertyValues = "property=value".getBytes(Charsets.UTF_8);
        when(manager.open(eq(LdbConstants.PROPERTIES))).thenReturn(new ByteArrayInputStream(propertyValues));

        // act
        Properties properties = PropertyUtil.load(manager);

        // assert
        assertThat((String)properties.get("property"), equalTo("value"));
    }
}
