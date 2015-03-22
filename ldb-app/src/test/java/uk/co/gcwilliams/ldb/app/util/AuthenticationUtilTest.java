package uk.co.gcwilliams.ldb.app.util;

import android.content.res.AssetManager;
import android.util.Base64;
import com.google.common.base.Charsets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * The authentication util tests
 *
 * Created by GWilliams on 22/03/2015.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Base64.class)
public class AuthenticationUtilTest {

    @Test
    public void testAuthenticationHeader() throws IOException {

        // arrange
        PowerMockito.mockStatic(Base64.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return "dW5hbWU6cHdvcmQ=".getBytes(Charsets.UTF_8);
            }
        });

        Properties properties = new Properties();

        // act
        AuthenticationUtil util = new AuthenticationUtil(properties);

        // assert
        assertThat(util.getAuthenticationHeader(), equalTo("Basic dW5hbWU6cHdvcmQ="));
    }
}
