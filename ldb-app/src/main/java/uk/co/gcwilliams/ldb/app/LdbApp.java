package uk.co.gcwilliams.ldb.app;

import android.app.Application;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;
import roboguice.RoboGuice;
import uk.co.gcwilliams.ldb.app.adapters.StationCodeAdapter;
import uk.co.gcwilliams.ldb.app.boards.StationBoardProvider;
import uk.co.gcwilliams.ldb.app.boards.StationBoardProviderImpl;
import uk.co.gcwilliams.ldb.app.util.AuthenticationUtil;
import uk.co.gcwilliams.ldb.app.util.PropertyUtil;
import uk.co.gcwilliams.ldb.request.HttpClient;
import uk.co.gcwilliams.ldb.request.HttpClientImpl;
import uk.co.gcwilliams.ldb.service.StationBoards;
import uk.co.gcwilliams.ldb.service.StationBoardsImpl;
import uk.co.gcwilliams.ldb.service.StationCodes;
import uk.co.gcwilliams.ldb.service.StationCodesImpl;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The LDB application
 *
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
            binder.bind(AuthenticationUtil.class).asEagerSingleton();
            binder.bind(HttpClient.class).toProvider(HttpClientProvider.class).asEagerSingleton();
            binder.bind(StationBoards.class).toProvider(StationBoardsProvider.class).asEagerSingleton();
            binder.bind(StationCodes.class).toProvider(StationCodesProvider.class).asEagerSingleton();
            binder.bind(StationCodeAdapter.class);
            binder.bind(StationBoardProvider.class).to(StationBoardProviderImpl.class).asEagerSingleton();
            binder.bind(Executor.class).toInstance(Executors.newSingleThreadExecutor());
        }
    }

    /**
     * The HTTP client provider
     *
     */
    private static class HttpClientProvider implements Provider<HttpClient> {

        private final HttpClient client;

        /**
         * Default constructor
         *
         * @param properties the properties
         * @param authenticationUtil the authentication utilities
         */
        @Inject
        public HttpClientProvider(Properties properties, AuthenticationUtil authenticationUtil) {
            String serviceUrl = properties.getProperty(LdbConstants.SERVICE_URL_KEY);
            String contextPath = properties.getProperty(LdbConstants.CONTEXT_PATH_KEY);
            client = new HttpClientImpl(serviceUrl, contextPath, 443, authenticationUtil.getAuthenticationHeader());
        }

        @Override
        public HttpClient get() {
            return client;
        }
    }

    /**
     * The station boards provider, configures the station boards
     *
     */
    private static class StationBoardsProvider implements Provider<StationBoards> {

        private final StationBoards boards;

        @Inject
        public StationBoardsProvider(HttpClient client) {
            boards = new StationBoardsImpl(client);
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
        public StationCodesProvider(HttpClient client) {
            codes = new StationCodesImpl(client);
        }

        @Override
        public StationCodes get() {
            return codes;
        }
    }
}