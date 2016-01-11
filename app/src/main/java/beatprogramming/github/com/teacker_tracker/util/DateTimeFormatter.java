package beatprogramming.github.com.teacker_tracker.util;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * - Para formatear las fechas
 */
public class DateTimeFormatter {

    private static final String DATETIME_JODA_FORMAT = "dd/MM/yyyy HH:mm";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "HH:mm";

    public static DateTime stringToDateTime(String dateTime){

        org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern(DATETIME_JODA_FORMAT);

        return dtf.parseDateTime(dateTime);

    }

    public static String dateTimeToTimeString(DateTime dateTime){

        org.joda.time.format.DateTimeFormatter dtfOut = DateTimeFormat.forPattern(TIME_FORMAT);

        return dtfOut.print(dateTime);

    }

    public static String timeToString(int hour, int minute) {

        DateTime dateTime = new DateTime().withTime(hour, minute, 0, 0);

        org.joda.time.format.DateTimeFormatter dtfOut = DateTimeFormat.forPattern(TIME_FORMAT);

        return dtfOut.print(dateTime);

    }

    public static String dateToString(int year, int month, int day) {

        DateTime dateTime = new DateTime().withDate(year, month, day);

        org.joda.time.format.DateTimeFormatter dtfOut = DateTimeFormat.forPattern(DATE_FORMAT);

        return dtfOut.print(dateTime);

    }
}