package sandromarques.dayoftheweek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DoomsdayAlgorithm {
    public record Pair(int year, int inc) {
    }

    public record PairDate(int day, int month) {
        @Override
        public String toString() {
            return day + "/" + month;
        }
    }

    private static final String DATE_PATTERN = "dd/MM";

    //Dates that share the same date of the week
    /*
    "4/4", "6/6", "8/8", "10/10", "12/12"
    //MORE SPECIAL DAYS :
    "14/3"  //PI day
    "9/5",  //"John works from 9/5 at a 7/11 store"
    "5/9"   // ---
    "7,11"  // ---
    "11,7"  // ---
    "4/7"   //july 4th , United States independence day
    "31/10" //Halloween
    "26/12"  //day after Christmas, boxing day

    Add finally if it's a non-leap year -> 03/01 and 28/02
    if it's a leap year -> 04/01 and 29/02
    */

    //Common doomsday's for all years
    private final PairDate[] doomsday = {
            new PairDate(14, 3),  //PI day
            new PairDate(4, 4),
            new PairDate(9, 5),   //"John works from 9/5 at a 7/11 store"
            new PairDate(6, 6),
            new PairDate(11, 7),  // ---
            new PairDate(4, 7),   //july 4th , United States independence day
            new PairDate(8, 8),
            new PairDate(5, 9),   // ---
            new PairDate(10, 10),
            new PairDate(31, 10), //halloween
            new PairDate(7, 11),  // ---
            new PairDate(26, 12), //day after Christmas, boxing day
            new PairDate(12, 12)
    };

    //Additional doomsday's only for leap years
    private final PairDate[] leapyear_doomsday = {
            new PairDate(4, 1),
            new PairDate(29, 2)
    };

    //Additional doomsday's only for non leap years
    private final PairDate[] non_leapyear_doomsday = {
            new PairDate(3, 1),
            new PairDate(28, 2)
    };

    public Weekday getWeekdayFromInputDate(InputDate date) {
        return getWeekdayFromNumber(calculate(date));
    }

    public int calculate(InputDate date) {
        int day = date.day;
        int month = date.month;
        int year = date.year;

        int century = getCentury(year);

        int doomsdayOfCentury = getDoomsdayOfCentury(century);

        //get the nearest year and get the difference between year and reference year
        Pair referenceYear = getReferenceYear(century, year);
        int yearsDiff = year - referenceYear.year();

        //get the nearest day/month and get the difference the nearest day/month
        InputDate referenceDayMonth = getClosestDoomsday(day, month, year);
        int diffFromDates = getDifferenceFromDates(day + "/" + month, referenceDayMonth.toString());

        //get the number of leap years
        int leapYears = Math.abs(referenceYear.year() - year) / 4;

        int doomsdayOfReferenceYear = removeGroupsOf7(doomsdayOfCentury + yearsDiff + leapYears + referenceYear.inc());
        //Timestamp of yt video = 6:30
        int total = doomsdayOfReferenceYear + diffFromDates;
        total = removeGroupsOf7(total);

        System.out.println("- Input year " + year + " is on the " + century + " century");
        System.out.println("- " + century + " (century) doomsday starts on " + getWeekdayFromNumber(doomsdayOfCentury) + " (" + doomsdayOfCentury + ")");
        System.out.print("- Closest reference year is " + referenceYear.year());
        System.out.println(" and starts on " + getWeekdayFromNumber(doomsdayOfCentury + referenceYear.inc()));
        System.out.println("- Closest reference date found : " + referenceDayMonth);
        System.out.println("- Difference between reference date and wanted date : " + diffFromDates);
        System.out.println("- Number of leap years between those dates : " + leapYears);
        System.out.println("- Doomsday on " + year + " is a " + getWeekdayFromNumber(doomsdayOfReferenceYear));
        System.out.println();

        return total;
    }

    public boolean isLeapYear(int year) {
        if (year % 400 == 0) return true;
        if (year % 100 == 0) return false;
        return year % 4 == 0;
    }

    /**
     * E.g :
     * n = 8 , returns 1
     * n = 14 , returns 0
     * n = -31 , returns 4
     *
     * @param n number to remove groups of 7
     * @return n , range 0 to 6 , both inclusive
     */
    public int removeGroupsOf7(int n) {
        while (n < 0) n += 7;
        while (n > 6) n -= 7;
        return n;
    }

    private int getDifferenceFromDates(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        try {
            java.util.Date d1 = sdf.parse(date1);
            java.util.Date d2 = sdf.parse(date2);

            // getting milliseconds for both dates
            long date1InMs = d1.getTime();
            long date2InMs = d2.getTime();

            // getting the diff between two dates.
            long timeDiff = date1InMs - date2InMs;

            // converting diff into days
            return (int) (timeDiff / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * From a certain date, find the nearest doomsday for that year
     *
     * @return
     */
    public InputDate getClosestDoomsday(int day, int month, int year) {
        boolean isLeapYear = isLeapYear(year);

        String date = day + "/" + month;

        LinkedList<PairDate> doomsday = new LinkedList<>(Arrays.asList(this.doomsday));
        if (isLeapYear) {
            Collections.addAll(doomsday, leapyear_doomsday);
        } else {
            Collections.addAll(doomsday, non_leapyear_doomsday);
        }

        int[] diffArr = new int[doomsday.size()];
        for (int i = 0; i < doomsday.size(); i++) {
            PairDate pd = doomsday.get(i);
            if (pd.day == day && pd.month == month) {
                return new InputDate(pd.day, pd.month);
            } else {
                diffArr[i] = getDifferenceFromDates(date, pd.toString());
            }
        }
        int min = 367; // or Integer.MAX_VALUE
        int idx = 0;
        //diffArr will have negative and positive differences , ideally we want 0 diff or -1/1 or -2/2 ... and so on
        // so for easier compare we just make all the numbers positive and picks 0,1,2...(the lowest available)
        for (int i = 0; i < diffArr.length; i++) {
            int dif = Math.abs(diffArr[i]);
            if (dif < min) {
                min = dif;
                idx = i;
            }
        }

        PairDate date1 = doomsday.get(idx);

        return new InputDate(date1.day, date1.month);
    }

    /*
    Known facts:
    ALL ZERO (Sunday) YEARS = 0,28,56,84
    INCREMENTED YEARS = 0,12,24,36,48,60,72,84,96
                  0(0),12(1),24(2),36(3),48(4),60(5),72(6),84(7),96(8)
     */

    /**
     * Find the closest reference year to a certain year (rounded down)
     *
     * @param century
     * @param year
     * @return
     */
    public Pair getReferenceYear(int century, int year) {
        int[] zeroYears = {0, 28, 56, 84};
        int[] incrementedYears = {0, 12, 24, 36, 48, 60, 72, 84, 96};

        HashMap<Integer, Integer> referenceYears = new HashMap<>();

        for (int zeroYear : zeroYears) {
            referenceYears.put(century + zeroYear, 0);
        }

        for (int i = 0; i < incrementedYears.length; i++) {
            int incYear = incrementedYears[i];
            //This is just for the number 84 to not be replaced with increment 7 since it's already increment 0
            if (referenceYears.containsKey(century + incYear)) {
                continue;
            }
            referenceYears.put(century + incYear, i);
        }

        //If year is already a reference year then no more calculations are needed
        if (referenceYears.containsKey(year)) {
            return new Pair(year, referenceYears.get(year));
        }

        int diff = Integer.MAX_VALUE;
        int bestReferenceYear = -1;
        for (int referenceYear : referenceYears.keySet()) {
            if (referenceYear > year) {
                continue;
            }

            int currentDiff = year - referenceYear;
            if (currentDiff < diff) {
                diff = currentDiff;
                bestReferenceYear = referenceYear;
            }
        }

        return new Pair(bestReferenceYear, referenceYears.get(bestReferenceYear));
    }

    /***
     * From a certain year returns its century
     */
    public int getCentury(int year) {
        return year - year % 100;
    }

    private Weekday getWeekdayFromNumber(int dayNum) {
        while (dayNum >= 7) dayNum -= 7;
        return Weekday.get(dayNum);
    }

    public int getDoomsdayOfCentury(int century) {
        //This would already be returned because of the default switch case, but it's better to verify nevertheless
        if (century < 1700) {
            return -1;
        }

        while (century > 2000) {
            century -= 400;
        }

        /*
        Known facts:
        1700 = SUNDAY
        1800 = FRIDAY
        1900 = WEDNESDAY
        2000 = TUESDAY
        (...repeats pattern)
         */
        switch (century) {
            case 1700 -> {
                return Weekday.SUNDAY.ordinal();
            }
            case 1800 -> {
                return Weekday.FRIDAY.ordinal();
            }
            case 1900 -> {
                return Weekday.WEDNESDAY.ordinal();
            }
            case 2000 -> {
                return Weekday.TUESDAY.ordinal();
            }
            default -> {
                return -1;
            }
        }
    }
}
