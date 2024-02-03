package sandromarques.dayoftheweek;

import java.text.SimpleDateFormat;

public class DateUtils {
    public static final String FULL_DATE_PATTERN = "dd/MM/yyyy";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE_PATTERN);

    /**
     * Parse a string to an InputDate object.
     *
     * @param input string to be parsed
     * @return InputDate object or null if string is in an invalid format
     */
    public static InputDate getDateFromInput(String input) {
        if (input == null) {
            return null;
        }

        try {
            String formattedString = sdf.format(sdf.parse(input));

            String[] split = formattedString.split("/");

            int day = Integer.parseInt(split[0]);
            int month = Integer.parseInt(split[1]);
            int year = Integer.parseInt(split[2]);

            //Compare input string with formatted string to check if they are equal
            if (!compareDates(input, day, month, year)) {
                return null;
            }

            return new InputDate(day, month, year);
        } catch (Exception ignored) {
            return null;
        }
    }

    /***
     * From inputDateString string , check if it corresponds to the parsed date format
     * Comparison must be made this way to ignore leading zeros on numbers
     * @return true if dates are equal
     */
    private static boolean compareDates(String inputDateString, int expectedDay, int expectedMonth, int expectedYear) {
        try {
            String[] split = inputDateString.split("/");

            int day = Integer.parseInt(split[0]);
            int month = Integer.parseInt(split[1]);
            int year = Integer.parseInt(split[2]);

            return day == expectedDay && month == expectedMonth && year == expectedYear;
        } catch (Exception ignored) {
            return false;
        }
    }
}
