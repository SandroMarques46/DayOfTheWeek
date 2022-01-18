package sandromarques.dayoftheweek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class DayOfTheWeek {

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
        "31/10" //halloween
        "26/12"  //day after Christmas, boxing day
        */

    private final String[] doomsday = {
            "14/3",  //PI day
            "4/4",
            "9/5",   //"John works from 9/5 at a 7/11 store"
            "6/6",
            "11,7",  // ---
            "4/7",   //july 4th , United States independence day
            "8/8",
            "5/9",   // ---
            "10/10",
            "31/10", //halloween
            "7,11",  // ---
            "26/12", //day after Christmas, boxing day
            "12/12"
    };

    private final String[] leapyear_doomsday = {
            "4/1",
            "29/2"
    };
    private final String[] non_leapyear_doomsday = {
            "3/1",
            "28/2"
    };
    private final boolean log;

    public DayOfTheWeek(boolean log) {
        this.log = log;
    }

    public static void main(String[] args) {
        DayOfTheWeek m = new DayOfTheWeek(true);
        m.run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("exit")) {
            //System.out.print("Enter date : ");
            //input = scanner.nextLine();
            input = "14/03/2001";
            String[] split = input.split("/");
            try {
                int day = Integer.parseInt(split[0]);
                int month = Integer.parseInt(split[1]);
                int year = Integer.parseInt(split[2]);
                System.out.println(input + " is " + getWeekdayFromNumber(calculate(day, month, year)));
                return;
            } catch (Exception e) {
                System.out.println("Invalid format!");
            }
        }
        System.out.println("Bye bye!");
    }

    public int calculate(int day, int month, int year) {
        int century = getCentury(year);
        int doomsdayWeekdayNum = getNumberFromYear(century);
        Pair referenceYear = getReferenceYear(year);
        System.out.println("- Input year " + year + " is on the " + century + " century");
        System.out.println("- " + century + " (century) doomsday starts on " + getWeekdayFromNumber(doomsdayWeekdayNum) + " (" + doomsdayWeekdayNum + ")");
        System.out.print("- Closest reference year is " + referenceYear.year);
        System.out.println(" and starts on " + getWeekdayFromNumber(doomsdayWeekdayNum + referenceYear.inc));
        String referenceDayMonth = getReferenceDayMonth(day, month);
        System.out.println("- Closest reference date found : " + referenceDayMonth);
        //TODO: leap years could be added to yearsDIff
        int yearsDiff = year - referenceYear.year;
        int referenceDiff = getDifferenceFromDates(referenceDayMonth, day + "/" + month);

        int leapYears = 0;
        if (referenceYear.year != year) {
            leapYears = Math.abs(referenceYear.year - year) / 4;
        }
        //Timestamp of yt video = 6:30
        int a = doomsdayWeekdayNum + yearsDiff + leapYears + referenceYear.inc;
        //take out the groups of 7 (range is 0 to 7)
        /*
        while (a > 6) a -= 7;
        while (a < 0) a += 7;
         */
        boolean isNegative = a < 0;
        a = Math.abs(a) % 7;
        if(isNegative) a *= -1;
        return a;
    }

    private int getDifferenceFromDates(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        try {
            java.util.Date d1 = sdf.parse(date1);
            java.util.Date d2 = sdf.parse(date2);

            // getting milliseconds for both dates
            long date1InMs = d1.getTime();
            long date2InMs = d2.getTime();

            // getting the diff between two dates.
            long timeDiff = Math.max(date1InMs, date2InMs) - Math.min(date1InMs, date2InMs);

            // converting diff into days
            return (int) (timeDiff / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //TODO: for now it only uses PI DAY , best would be to find the closet date with the code below
    public String getReferenceDayMonth(int day, int month) {
        return "14/3";
        /*
        int[] diffArr = new int[doomsday.length];
        //TODO: also add leap year dates for check
        for (int i = 0; i < doomsday.length; i++) {
            String doomsdayDate = doomsday[i];
            int doomsdayDay = Integer.parseInt(doomsdayDate.split("/")[0]);
            int doomsdayMonth = Integer.parseInt(doomsdayDate.split("/")[1]);
            if (doomsdayDay == day && doomsdayMonth == month) {
                return doomsdayDay + "" + doomsdayMonth;
            } else {
                //TODO : calculate difference between days and months
                diffArr[i] = 123123;
            }
        }
        int min = 367;
        int idx = 0;
        for (int i = 0; i < diffArr.length; i++) {
            int dif = diffArr[i];
            if (dif < min) {
                min = dif;
                idx = i;
            }
        }
        return doomsday[idx];
         */
    }

    /*
    ALL ZERO YEARS = 0,28,56,84
    INCREMENTED YEARS = 0,12,24,36,48,60,72,84,96
                  0(0),12(1),24(2),36(3),48(4),60(5),72(6),84(7),96(8)
     */
    public Pair getReferenceYear(int year) {
        int century = getCentury(year);
        int diffZeroYears = 101;
        Pair valZeroYears = null;
        int[] zero_years = {0, 28, 56, 84};
        for (int curr : zero_years) {
            int val = century + curr;
            if (val == year) {
                return new Pair(val, 0);
            } else {
                int diff = Math.abs(val - year);
                if (diff < diffZeroYears) {
                    diffZeroYears = diff;
                    valZeroYears = new Pair(val, 0);
                } else { //stop searching since the closest year (lowest difference) was already found previously
                    break;
                }
            }
        }
        int diffIncYears = 101;
        Pair valIncYears = null;
        int[] inc_years = {0, 12, 24, 36, 48, 60, 72, 84, 96};
        for (int i = 0; i < inc_years.length; i++) {
            int curr = inc_years[i];
            int val = century + curr;
            if (val == year) {
                return new Pair(val, i);
            } else {
                int diff = Math.abs(val - year);
                if (diff < diffIncYears) {
                    diffIncYears = diff;
                    valIncYears = new Pair(val, i);
                } else { //stop searching since the closest year was already found previously
                    break;
                }
            }
        }
        if (diffIncYears < diffZeroYears) {
            return valIncYears;
        } else {
            return valZeroYears;
        }
    }

    /**
     * From year parameter returns it's century , e.g
     * getCentury(2020) == 2000
     * getCentury(1987) == 1900
     */
    public int getCentury(int year) {
        return year - year % 100;
    }

    /*
    18th june 1976
    ALL ZERO YEARS = 0,28,56,84
    CONSECUTIVE = 0,12,24,36,48,60,72,84,96
                  0(0),12(1),24(2),36(3),48(4),60(5),72(6),84(7),96(8)
    1700 = SUNDAY
    1800 = FRIDAY
    1900 = WEDNESDAY
    2000 = TUESDAY
    (...repeats)

    for example doomsday in 1988 :
        1900 = wednesday = 3
        1988-1984 = 4 years diff (since 1984 is a 0 year)
        leap year = 4 / 4 = 1
        TOTAL = 3 + 4 + 1 = 8 (take out groups of 7) = 1 = MONDAY
    for example 18th june 1976 :
        1900 = wednesday = 3
        1972 = 6 (check CONSECUTIVE pattern)
        1976-1972 = 4 years diff
        leap year = 4 / 4 = 1
        = 3 + 6 + 4 + 1 = 14 (take out groups of 7) = 0 = SUNDAY (DOOMSDAY)
        Now that we know the doomsday value , we just need to calculate from the closest doomsday.
        In this case the closest to 18th june (18/6) is 6/6
        6/6 (DOOMSDAY) = SUNDAY = 0
            then 18/6 = 0 + 18-6 = 12 (take out groups of 7) = 5 = FRIDAY
     */

    private Weekday getWeekdayFromNumber(int dayNum) {
        while (dayNum >= 7) dayNum -= 7;
        switch (dayNum) {
            case 0 -> {
                return Weekday.SUNDAY;
            }
            case 1 -> {
                return Weekday.MONDAY;
            }
            case 2 -> {
                return Weekday.TUESDAY;
            }
            case 3 -> {
                return Weekday.WEDNESDAY;
            }
            case 4 -> {
                return Weekday.THURSDAY;
            }
            case 5 -> {
                return Weekday.FRIDAY;
            }
            case 6 -> {
                return Weekday.SATURDAY;
            }
        }
        return null;
    }

    private int getNumberFromWeekday(Weekday weekday) {
        switch (weekday) {
            case SUNDAY -> {
                return 0;
            }
            case MONDAY -> {
                return 1;
            }
            case TUESDAY -> {
                return 2;
            }
            case WEDNESDAY -> {
                return 3;
            }
            case THURSDAY -> {
                return 4;
            }
            case FRIDAY -> {
                return 5;
            }
            case SATURDAY -> {
                return 6;
            }
        }
        return -1;
    }

    public int getNumberFromYear(int year) {
        if (year < 1700) return -1;
        return getNumberFromWeekday(getWeekdayFromYear(year));
    }

    /*
    1700 = SUNDAY
    1800 = FRIDAY
    1900 = WEDNESDAY
    2000 = TUESDAY
    (...repeats)
    2100 = SUNDAY
    2200 = FRIDAY
    2300 = WEDNESDAY
    2400 = TUESDAY
     */
    private Weekday getWeekdayFromYear(int year) {
        do {
            switch (year) {
                case 1700 -> {
                    return Weekday.SUNDAY;
                }
                case 1800 -> {
                    return Weekday.FRIDAY;
                }
                case 1900 -> {
                    return Weekday.WEDNESDAY;
                }
                case 2000 -> {
                    return Weekday.TUESDAY;
                }
            }
            year -= 400;
        } while (true);
    }
}
