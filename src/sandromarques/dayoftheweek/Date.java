package sandromarques.dayoftheweek;

enum Weekday {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

public class Date {
    public final int day;
    public final int month;
    public final int year;
    public final Weekday weekday;

    public Date(int day, int month, int year, Weekday weekday) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.weekday = weekday;
    }

    @Override
    public String toString() {
        return day + "/"
                + month + "/"
                + year + " "
                + "(" + weekday + ")";
    }
}
