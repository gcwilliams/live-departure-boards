package uk.co.gcwilliams.ldb.app;

/**
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

        public static final String FROM = "from";

        public static final String TO = "to";
    }
}