package uk.co.gcwilliams.ldb.app;

/**
 * The constants used throughout the app
 *
 * @author Gareth Williams
 */
public final class LdbConstants {

    private LdbConstants() {} // static

    public static final String LOG_TAG = "LDB";

    public static final String PROPERTIES = "runtime.properties";

    public static final String SERVICE_URL_KEY = "service.url";

    public static final String CONTEXT_PATH_KEY = "service.context.path";

    public static final String USERNAME_KEY = "username";

    public static final String PASSWORD_KEY = "password";

    public final class Search {

        private Search() {} // static

        public static final String DEPARTURES = "departures";

        public static final String STATION_CODE = "station.code";

        public static final String OPTIONAL_STATION_CODE = "optional.station.code";

        public static final String BOARD = "board";

        public static final int BOARD_MSG_ID = 125887;
    }

    public final class Detail {

        private Detail() {} // static

        public static final String SERVICE_ID = "service.id";

        public static final String SERVICE_DETAIL = "detail";

        public static final int SERVICE_DETAIL_MSG_ID = 125888;
    }
}