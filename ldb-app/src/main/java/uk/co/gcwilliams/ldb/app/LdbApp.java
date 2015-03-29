package uk.co.gcwilliams.ldb.app;

import android.app.Application;
import android.net.http.AndroidHttpClient;
import com.google.common.base.Charsets;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;
import android.util.Base64;
import roboguice.RoboGuice;
import uk.co.gcwilliams.ldb.app.adapters.StationCodeAdapter;
import uk.co.gcwilliams.ldb.app.tasks.ArrivalStationBoardTask;
import uk.co.gcwilliams.ldb.app.tasks.DepartureStationBoardTask;
import uk.co.gcwilliams.ldb.app.util.AuthenticationUtil;
import uk.co.gcwilliams.ldb.app.util.PropertyUtil;
import uk.co.gcwilliams.ldb.service.StationBoards;
import uk.co.gcwilliams.ldb.service.StationCodes;
import uk.co.gcwilliams.ldb.service.impl.StationBoardsImpl;
import uk.co.gcwilliams.ldb.service.impl.StationCodesImpl;

import java.util.Properties;

import static java.lang.String.format;

/**
 * @author Gareth Williams
 */
public class LdbApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.setBaseApplicationInjector(
            this,
            RoboGuice.DEFAULT_STAGE,
            RoboGuice.newDefaultRoboModule(this),
            new LdbModule(PropertyUtil.load(getAssets()))
        );
    }

    /**
     * The LDB module
     *
     */
    private static class LdbModule implements Module {

        private final Properties properties;

        /**
         * Default constructor
         *
         * @param properties the properties
         */
        public LdbModule(Properties properties) {
            this.properties = properties;
        }

        @Override
        public void configure(Binder binder) {
            binder.bind(Properties.class).toInstance(properties);
            binder.bind(AuthenticationUtil.class);
            binder.bind(AndroidHttpClient.class).toInstance(AndroidHttpClient.newInstance("Android"));
            binder.bind(StationBoards.class).toProvider(StationBoardsProvider.class).asEagerSingleton();
            binder.bind(StationCodes.class).toProvider(StationCodesProvider.class).asEagerSingleton();
            binder.bind(StationCodeAdapter.class);
            binder.bind(DepartureStationBoardTask.class);
            binder.bind(ArrivalStationBoardTask.class);
        }
    }

    /**
     * The station boards provider, configures the station boards
     *
     */
    private static class StationBoardsProvider implements Provider<StationBoards> {

        private final StationBoards boards;

        @Inject
        public StationBoardsProvider(
                AndroidHttpClient httpClient,
                Properties properties,
                AuthenticationUtil authenticationUtil) {
            String serviceUrl = properties.getProperty(LdbConstants.SERVICE_URL_KEY);
            boards = new StationBoardsImpl(httpClient, serviceUrl, authenticationUtil.getAuthenticationHeader());
        }

        @Override
        public StationBoards get() {
            return boards;
        }
    }

    /**
     * The station codes provider, configures the station codes
     *
     */
    private static class StationCodesProvider implements Provider<StationCodes> {

        private final StationCodes codes;

        @Inject
        public StationCodesProvider(
                AndroidHttpClient httpClient,
                Properties properties,
                AuthenticationUtil authenticationUtil) {
            String serviceUrl = properties.getProperty(LdbConstants.SERVICE_URL_KEY);
            codes = new StationCodesImpl(httpClient, serviceUrl, authenticationUtil.getAuthenticationHeader());
        }

        @Override
        public StationCodes get() {
            return codes;
        }
    }
}