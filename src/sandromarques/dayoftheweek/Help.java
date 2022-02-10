package sandromarques.dayoftheweek;

public class Help {
    public static void main(String[] args) {
        Help help = new Help();
        help.run();
    }

    private void run() {
        System.out.println("Thing you NEED to know : ");
        System.out.println("Doomsday on centuries\n\t1700-SUNDAY\n\t1800-FRIDAY\n\t1900-WEDNESDAY\n\t2000-TUESDAY\n\t(repeats every 4 years)");
        System.out.println("Example : 18/06/1976");
        System.out.println("\t1º - Get doomsday of century(1900 = WEDNESDAY(3))");
        System.out.println("\t2º - Get closest reference year (1972 with an increment of 6)");
        System.out.println("\t3º - Doomsday of reference year (doomsdayCentury + yearsDiff + leapYears + increment)");
        System.out.println("\t\t = 3 + (1976-1972=4) + (4/4) + 6 = 14 (taking groups of 7) = 0 (0=SUNDAY)");
        System.out.println("\t4º - Get the closest doomsday date (day/month)");
        System.out.println("\t\t closest of 18/6 is 6/6 (difference between those dates is 12)");
        System.out.println("\t\t Taking groups of 7 , 12 = 5 , 18/06/1976 = (5) FRIDAY");


        //EXAMPLES :
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
             */

    }
}
