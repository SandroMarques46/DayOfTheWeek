package sandromarques.dayoftheweek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class DayOfTheWeek {
    private final DoomsdayAlgorithm doomsdayAlgorithm = new DoomsdayAlgorithm();

    public static void main(String[] args) {
        DayOfTheWeek m = new DayOfTheWeek();
        m.run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equalsIgnoreCase("exit")) {
            System.out.print("Enter date (" + DateUtils.FULL_DATE_PATTERN + ") : ");
            input = scanner.nextLine();

            try {
                InputDate date = DateUtils.getDateFromInput(input);

                if (date == null) {
                    System.out.println("Date is in incorrect format. Please try again!");
                    continue;
                }

                System.out.println(input + " is " + doomsdayAlgorithm.getWeekdayFromInputDate(date));
            } catch (Exception e) {
                System.out.println("Invalid date! Try again or write EXIT to terminate program");
            }
        }
        System.out.println("Bye bye!");
    }
}