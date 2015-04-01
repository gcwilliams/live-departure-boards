package uk.co.gcwilliams.ldb.service;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * A utility for parsing time
 *
 * @author Gareth Williams
 */
class TimeParseUtil {

    private static final DateTimeZone LONDON = DateTimeZone.forID("Europe/London");

    private static final DateTimeFormatter TIME_PARSER = DateTimeFormat.forPattern("HH:mm").withZone(LONDON);

    private TimeParseUtil() {} // static class

    /**
     * Parses the time HH:MM into a JODA date time, with the time zone of Europe/London
     *
     * @param maybeTime the string which may be a time
     * @return the DateTime, or null
     */
    static DateTime tryParseTime(String maybeTime) {
        try {
            DateTime now = DateTime.now().withZone(LONDON);
            return TIME_PARSER.parseDateTime(maybeTime).withDate(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth());
        } catch (NullPointerException | IllegalArgumentException | UnsupportedOperationException ex) {
            // ignore
        }
        return null;
    }
}
