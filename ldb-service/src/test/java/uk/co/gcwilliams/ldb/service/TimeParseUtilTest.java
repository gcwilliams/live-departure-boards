package uk.co.gcwilliams.ldb.service;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * The time parse util tests
 *
 * @author Gareth Williams
 */
public class TimeParseUtilTest {

    @Test
    public void testParseTimeNullValue() {

        // arrange
        String time = null;

        // act
        DateTime result = TimeParseUtil.tryParseTime(time);

        // assert
        assertThat(result, nullValue());
    }

    @Test
    public void testParseTimeEmptyString() {

        // arrange
        String time = "";

        // act
        DateTime result = TimeParseUtil.tryParseTime(time);

        // assert
        assertThat(result, nullValue());
    }

    @Test
    public void testParseTime() {

        // arrange
        DateTimeZone london = DateTimeZone.forID("Europe/London");
        DateTime now = DateTime.now().withZone(london);
        String time = "14:25";

        // act
        DateTime result = TimeParseUtil.tryParseTime(time);

        // assert
        assertThat(result.getYear(), equalTo(now.getYear()));
        assertThat(result.getMonthOfYear(), equalTo(now.getMonthOfYear()));
        assertThat(result.getDayOfMonth(), equalTo(now.getDayOfMonth()));
        assertThat(result.getHourOfDay(), equalTo(14));
        assertThat(result.getMinuteOfHour(), equalTo(25));
    }
}
