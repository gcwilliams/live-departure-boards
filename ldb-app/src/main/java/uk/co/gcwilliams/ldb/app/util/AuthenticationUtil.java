package uk.co.gcwilliams.ldb.app.util;

import android.util.Base64;
import com.google.common.base.Charsets;
import uk.co.gcwilliams.ldb.app.LdbConstants;

import javax.inject.Inject;
import java.util.Properties;

import static java.lang.String.format;

/**
 * The authentication utils
 *
 * Created by GWilliams on 22/03/2015.
 */
public class AuthenticationUtil {

    private final String authenticationHeader;

    /**
     * Default constructor
     *
     * @param properties the properties containing the authentication credentials
     */
    @Inject
    public AuthenticationUtil(Properties properties) {
        String username = properties.getProperty(LdbConstants.USERNAME_KEY);
        String password = properties.getProperty(LdbConstants.PASSWORD_KEY);
        byte[] usernameAndPassword = format("%s:%s", username, password).getBytes(Charsets.UTF_8);
        byte[] authHeader = Base64.encode(usernameAndPassword, Base64.DEFAULT);
        this.authenticationHeader = format("Basic %s", new String(authHeader, Charsets.UTF_8));
    }

    /**
     * Gets the authentication header
     *
     * @return the authentication header
     */
    public String getAuthenticationHeader() {
        return authenticationHeader;
    }
}
