import org.junit.Assert;
import org.junit.Test;
import sandromarques.dayoftheweek.DayOfTheWeek;

public class DayTest {
    DayOfTheWeek main = new DayOfTheWeek(false);

    /*
    0 SUNDAY
    1 MONDAY
    2 TUESDAY
    3 WEDNESDAY
    4 THURSDAY
    5 FRIDAY
    6 SATURDAY
    */
    @Test
    public void dayTest() {
        Assert.assertEquals(2,main.calculate(14,3,2000)); // TUESDAY (2)
        Assert.assertEquals(3,main.calculate(14,3,2001)); // WEDNESDAY (3)
        Assert.assertEquals(4,main.calculate(14,3,2002)); // THURSDAY (4)
        Assert.assertEquals(5,main.calculate(14,3,2003)); // FRIDAY (5)
        //leap year so instead of saturday (friday + 1) we add 2 days
        Assert.assertEquals(0,main.calculate(14,3,2004)); // SUNDAY (0)
        Assert.assertEquals(1,main.calculate(14,3,2005)); // MONDAY (1)

        Assert.assertEquals(2,main.calculate(14,3,1972)); // TUESDAY (2)
        Assert.assertEquals(5,main.calculate(14,3,2031)); // FRIDAY (5)
        Assert.assertEquals(0,main.calculate(14,3,1976)); // SUNDAY (0)
    }

    @Test
    public void yearTest() {
        Assert.assertEquals(-1,main.getNumberFromYear(1699)); // < 1700

        Assert.assertEquals(0,main.getNumberFromYear(1700)); // SUNDAY (0)
        Assert.assertEquals(5,main.getNumberFromYear(1800)); // FRIDAY (5)
        Assert.assertEquals(3,main.getNumberFromYear(1900)); // WEDNESDAY (3)
        Assert.assertEquals(2,main.getNumberFromYear(2000)); // TUESDAY (2)

        Assert.assertEquals(0,main.getNumberFromYear(2100)); // SUNDAY (0)
        Assert.assertEquals(5,main.getNumberFromYear(2200)); // SUNDAY (0)
        Assert.assertEquals(3,main.getNumberFromYear(2300)); // SUNDAY (0)
        Assert.assertEquals(2,main.getNumberFromYear(2400)); // SUNDAY (0)
    }

    @Test
    public void centuryTest() {
        Assert.assertEquals(1600,main.getCentury(1699));
        Assert.assertEquals(1700,main.getCentury(1787));
        Assert.assertEquals(1700,main.getCentury(1700));
        Assert.assertEquals(2000,main.getCentury(2020));
    }

    @Test
    public void closestReferenceYearTest() {
        //exact zero years
        Assert.assertEquals(1900,main.getReferenceYear(1900).year);
        Assert.assertEquals(1928,main.getReferenceYear(1928).year);
        Assert.assertEquals(1956,main.getReferenceYear(1956).year);
        Assert.assertEquals(1984,main.getReferenceYear(1984).year);
        //exact incremented years
        Assert.assertEquals(1912,main.getReferenceYear(1912).year);
        Assert.assertEquals(1924,main.getReferenceYear(1924).year);
        Assert.assertEquals(1936,main.getReferenceYear(1936).year);
        Assert.assertEquals(1948,main.getReferenceYear(1948).year);
        Assert.assertEquals(1960,main.getReferenceYear(1960).year);
        Assert.assertEquals(1972,main.getReferenceYear(1972).year);
        Assert.assertEquals(1984,main.getReferenceYear(1984).year);
        Assert.assertEquals(1996,main.getReferenceYear(1996).year);
        //near
        Assert.assertEquals(1900,main.getReferenceYear(1901).year);
        Assert.assertEquals(1900,main.getReferenceYear(1902).year);
        Assert.assertEquals(1900,main.getReferenceYear(1903).year);
        Assert.assertEquals(1900,main.getReferenceYear(1904).year);
        Assert.assertEquals(1900,main.getReferenceYear(1905).year);
        Assert.assertEquals(1900,main.getReferenceYear(1906).year);
        Assert.assertEquals(1912,main.getReferenceYear(1911).year);
        Assert.assertEquals(1912,main.getReferenceYear(1910).year);
        Assert.assertEquals(1912,main.getReferenceYear(1909).year);
        Assert.assertEquals(1912,main.getReferenceYear(1908).year);
        Assert.assertEquals(1912,main.getReferenceYear(1907).year);
    }
}
