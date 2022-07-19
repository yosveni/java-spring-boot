package com.linkapital.core.util;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static io.jsonwebtoken.lang.Strings.hasText;
import static java.lang.Integer.parseInt;
import static java.util.Calendar.YEAR;

/**
 * Has the responsibility to make operations over dates.
 */
public class DateUtil {

    private DateUtil() {
    }

    /**
     * Converts a string to a date with the "yyyy-MM-dd" pattern.
     *
     * @param date {@link String} The date to parse.
     * @return {@link Date}
     * @throws ParseException If the beginning of the specified String object cannot be parsed.
     */
    public static Date convert(String date) throws ParseException {
        //todo sacar estos formatos de fecha del util
        var format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return format.parse(date);
    }

    /**
     * Parse a date to a string with the provided pattern.
     *
     * @param date    {@link Date} The date to format.
     * @param pattern {@link String} The format to apply.
     * @return {@link Date}
     */
    public static @NotNull String format(Date date, String pattern) {
        var format = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return format.format(date);
    }

    /**
     * Converts a string to a date with the "MM-yyyy" pattern. Ex: novembro 2021
     *
     * @param date {@link String} The date to parse.
     * @return {@link Date} The Date object parsed from String object.
     */
    public static LocalDate parseToLocalDate(String date) {
        var now = LocalDate.now();
        if (!hasText(date))
            return now;

        var array = date.trim().split(" ");
        if (array.length < 2)
            return now;

        var m = array[0];
        var year = array[1];

        if (!hasText(m) || !hasText(year))
            return now;

        var month = switch (m) {
            case "janeiro" -> 1;
            case "fevereiro" -> 2;
            case "março" -> 3;
            case "abril" -> 4;
            case "maio" -> 5;
            case "junho" -> 6;
            case "julho" -> 7;
            case "agosto" -> 8;
            case "setembro" -> 9;
            case "outubro" -> 10;
            case "novembro" -> 11;
            default -> 12;
        };

        return LocalDate.of(parseInt(year), month, 1);
    }

    /**
     * Converts a string to a date with the "MM-yyyy" pattern. Ex: 10-2021
     *
     * @param date {@link String} The date to parse.
     * @return {@link Date} The Date object parsed from String object.
     * @throws ParseException If the beginning of the specified String object cannot be parsed.
     */
    public static LocalDate convertToFormat(String date) throws ParseException {
        var format = new SimpleDateFormat("MM-yyyy", Locale.ENGLISH);
        var parsed = format.parse(date);

        return LocalDate.parse(parsed.toString());
    }

    /**
     * Converts a string to a date with the desired pattern.
     *
     * @param date    {@link String} The date to parse.
     * @param pattern {@link String} The format in which the date should be parsed.
     * @return {@link Date} The Date object parsed from String object.
     */
    public static Date convertToFormat(String date, String pattern) throws ParseException {
        var format = new SimpleDateFormat(pattern, Locale.ENGLISH);
        format.setLenient(false);

        return format.parse(date);
    }

    /**
     * Gets number of years between two dates.
     *
     * @param init {@link Date} The init date.
     * @param end  {@link Date} The end date.
     * @return {@link Integer}   The primitive integer. The number of years between first and last date.
     */
    public static int yearsDiff(Date init, Date end) {
        var a = Calendar.getInstance();
        var b = Calendar.getInstance();

        a.setTime(init);
        b.setTime(end);

        var diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);

        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE)))
            diff--;

        return diff;
    }

    public static int getYear(Date date) {
        var calendar = Calendar.getInstance();
        calendar.setTime(date == null
                ? new Date()
                : date);

        return calendar.get(YEAR);
    }

    public static int getMonth(Date date) {
        if (date == null)
            return 1;

        var calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.MONTH) + 1;
    }

    public static @NotNull Date addMonth(Date date, int amount) {
        var calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);

        return calendar.getTime();
    }

    public static long calculateMonth(Date end, Date init) {
        var endDate = end == null
                ? 0L
                : end.getTime();
        var initDate = init == null
                ? 0L
                : init.getTime();
        var diff = endDate - initDate;
        var days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        return days == 0
                ? 0
                : Math.round((float) days / 30);
    }

    public static long calculateDays(Date end, Date init) {
        var endDate = end == null
                ? 0L
                : end.getTime();
        var initDate = init == null
                ? 0L
                : init.getTime();
        var diff = endDate - initDate;

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

}
