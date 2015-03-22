package uk.co.gcwilliams.ldb.app.util;

import android.content.res.AssetManager;
import android.util.Log;
import uk.co.gcwilliams.ldb.app.LdbConstants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A properties utility class
 *
 * Created by GWilliams on 22/03/2015.
 */
public class PropertyUtil {

    private PropertyUtil() { } // static

    /**
     * Loads the runtime properties file
     *
     * @param manager the asset manager
     * @return the properties
     */
    public static Properties load(AssetManager manager) {
        Properties properties = new Properties();
        try (InputStream input = manager.open(LdbConstants.PROPERTIES)) {
            properties.load(input);
        } catch (IOException exception) {
            Log.e(LdbConstants.LOG_TAG, "unable to load runtime properties", exception);
        }
        return properties;
    }
}
