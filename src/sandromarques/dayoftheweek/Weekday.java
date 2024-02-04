package sandromarques.dayoftheweek;

public enum Weekday {
    SUNDAY, //0
    MONDAY, //1
    TUESDAY, //2
    WEDNESDAY, //3
    THURSDAY, //4
    FRIDAY, //5
    SATURDAY; //6

    private static final Weekday[] values = values();

    public static Weekday get(int dayOfTheWeek) {
        if (dayOfTheWeek < 0 || dayOfTheWeek >= values.length) {
            return null;
        }
        return values[dayOfTheWeek];
    }
}
