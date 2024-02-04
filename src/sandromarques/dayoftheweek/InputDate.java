package sandromarques.dayoftheweek;

public class InputDate {
    public final int day;
    public final int month;
    public final int year;

    public InputDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public InputDate(int day, int month) {
        this(day, month, 0);
    }

    @Override
    public String toString() {
        return year != 0 ?
                day + "/" + month + "/" + year
                : day + "/" + month;
    }
}
