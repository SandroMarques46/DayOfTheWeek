import org.junit.Assert;
import org.junit.Test;
import sandromarques.dayoftheweek.DateUtils;
import sandromarques.dayoftheweek.DayOfTheWeek;
import sandromarques.dayoftheweek.InputDate;

public class DayTest {
    DayOfTheWeek main = new DayOfTheWeek(false);

    @Test
    public void getReferenceDoomsdayTest() {
        Assert.assertEquals("14/3", main.getClosestDoomsday(7, 3, false));
        Assert.assertEquals("28/2", main.getClosestDoomsday(6, 3, false));
        Assert.assertEquals("14/3", main.getClosestDoomsday(15, 3, false));
        Assert.assertEquals("10/10", main.getClosestDoomsday(10, 10, false));
        Assert.assertEquals("6/6", main.getClosestDoomsday(15, 6, false));
        Assert.assertEquals("4/7", main.getClosestDoomsday(31, 6, false));
    }

    @Test
    public void getDateFromInput_Success() {
        InputDate date1 = DateUtils.getDateFromInput("01/01/2020");
        Assert.assertEquals(date1.day, 1);
        Assert.assertEquals(date1.month, 1);
        Assert.assertEquals(date1.year, 2020);

        InputDate date2 = DateUtils.getDateFromInput("1/1/2020");
        Assert.assertEquals(date2.day, 1);
        Assert.assertEquals(date2.month, 1);
        Assert.assertEquals(date2.year, 2020);
    }

    @Test
    public void getDateFromInput_InvalidFormat() {
        Assert.assertNull(DateUtils.getDateFromInput("1/2020"));
        Assert.assertNull(DateUtils.getDateFromInput("1-1-2020"));
        Assert.assertNull(DateUtils.getDateFromInput("1 1 2020"));
        Assert.assertNull(DateUtils.getDateFromInput("2020/1/1"));
    }

    @Test
    public void leapYearsTest() {
        for (int year = 1904; year <= 2048; year += 4) {
            Assert.assertTrue(main.isLeapYear(year));
        }
        for (int year = 1001; year <= 2048; year += 4) {
            Assert.assertFalse(main.isLeapYear(year));
        }
        Assert.assertFalse(main.isLeapYear(1900));
        Assert.assertFalse(main.isLeapYear(2100));
    }

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
    public void doomsdayTest() {
        Assert.assertEquals(2, main.calculate(new InputDate(14, 3, 2000))); // TUESDAY (2)
        Assert.assertEquals(3, main.calculate(new InputDate(14, 3, 2001))); // WEDNESDAY (3)
        Assert.assertEquals(4, main.calculate(new InputDate(14, 3, 2002))); // THURSDAY (4)
        Assert.assertEquals(5, main.calculate(new InputDate(14, 3, 2003))); // FRIDAY (5)
        //leap year so instead of saturday (friday + 1) we add 2 days
        Assert.assertEquals(0, main.calculate(new InputDate(14, 3, 2004))); // SUNDAY (0)
        Assert.assertEquals(1, main.calculate(new InputDate(14, 3, 2005))); // MONDAY (1)
        //random years
        Assert.assertEquals(2, main.calculate(new InputDate(14, 3, 1972))); // TUESDAY (2)
        Assert.assertEquals(0, main.calculate(new InputDate(14, 3, 1976))); // SUNDAY (0)
        Assert.assertEquals(5, main.calculate(new InputDate(14, 3, 2031))); // FRIDAY (5)
    }

    @Test
    public void dayTest() {
        //random dates
        Assert.assertEquals(3, main.calculate(new InputDate(14, 3, 2001))); // WEDNESDAY (3)
        Assert.assertEquals(6, main.calculate(new InputDate(24, 3, 2001))); // SUNDAY (6)
        Assert.assertEquals(1, main.calculate(new InputDate(24, 3, 2003))); // MONDAY (1)
        Assert.assertEquals(3, main.calculate(new InputDate(24, 3, 2004))); // WEDNESDAY (3)
        Assert.assertEquals(0, main.calculate(new InputDate(14, 4, 1985))); // SUNDAY (0)
        Assert.assertEquals(3, main.calculate(new InputDate(15, 5, 1985))); // WEDNESDAY (3)
        Assert.assertEquals(6, main.calculate(new InputDate(28, 9, 1985))); // SUNDAY (6)
        Assert.assertEquals(1, main.calculate(new InputDate(12, 1, 1970)));
        Assert.assertEquals(4, main.calculate(new InputDate(12, 1, 2023)));

        Assert.assertEquals(3, main.calculate(new InputDate(20, 1, 1971)));
        Assert.assertEquals(2, main.calculate(new InputDate(14, 10, 1980)));
        Assert.assertEquals(6, main.calculate(new InputDate(3, 4, 1982)));
        Assert.assertEquals(1, main.calculate(new InputDate(3, 2, 1992)));
        Assert.assertEquals(2, main.calculate(new InputDate(10, 4, 2001)));
        Assert.assertEquals(0, main.calculate(new InputDate(27, 6, 2010)));
        Assert.assertEquals(1, main.calculate(new InputDate(14, 2, 2011)));
        Assert.assertEquals(6, main.calculate(new InputDate(1, 1, 2011)));
        Assert.assertEquals(5, main.calculate(new InputDate(1, 1, 2010)));
        Assert.assertEquals(2, main.calculate(new InputDate(27, 12, 2011)));
        Assert.assertEquals(4, main.calculate(new InputDate(7, 6, 2012)));
        Assert.assertEquals(5, main.calculate(new InputDate(28, 7, 2017)));
        Assert.assertEquals(3, main.calculate(new InputDate(31, 1, 2018)));
        Assert.assertEquals(5, main.calculate(new InputDate(31, 5, 2024)));
        Assert.assertEquals(1, main.calculate(new InputDate(14, 10, 2024)));
        Assert.assertEquals(5, main.calculate(new InputDate(29, 11, 2024)));
        Assert.assertEquals(3, main.calculate(new InputDate(29, 7, 2043)));

        Assert.assertEquals(4, main.calculate(new InputDate(3, 3, 2011)));
        Assert.assertEquals(5, main.calculate(new InputDate(3, 3, 2012)));

        Assert.assertEquals(5, main.calculate(new InputDate(11, 3, 2044)));
        Assert.assertEquals(3, main.calculate(new InputDate(29, 3, 2084)));
        Assert.assertEquals(2, main.calculate(new InputDate(26, 4, 2095)));
        Assert.assertEquals(5, main.calculate(new InputDate(20, 5, 2067)));
        Assert.assertEquals(0, main.calculate(new InputDate(3, 8, 2064)));
        Assert.assertEquals(5, main.calculate(new InputDate(8, 8, 2092)));
        Assert.assertEquals(6, main.calculate(new InputDate(8, 9, 2096)));
        Assert.assertEquals(3, main.calculate(new InputDate(2, 11, 2095)));
        Assert.assertEquals(6, main.calculate(new InputDate(27, 12, 2087)));
    }


    @Test
    public void yearTest() {
        Assert.assertEquals(-1, main.getYearDigit(1699)); // < 1700

        Assert.assertEquals(0, main.getYearDigit(1700)); // SUNDAY (0)
        Assert.assertEquals(5, main.getYearDigit(1800)); // FRIDAY (5)
        Assert.assertEquals(3, main.getYearDigit(1900)); // WEDNESDAY (3)
        Assert.assertEquals(2, main.getYearDigit(2000)); // TUESDAY (2)

        Assert.assertEquals(0, main.getYearDigit(2100)); // SUNDAY (0)
        Assert.assertEquals(5, main.getYearDigit(2200)); // FRIDAY (5)
        Assert.assertEquals(3, main.getYearDigit(2300)); // WEDNESDAY (3)
        Assert.assertEquals(2, main.getYearDigit(2400)); // TUESDAY (2)
    }

    @Test
    public void centuryTest() {
        Assert.assertEquals(1600, main.getCentury(1699));
        Assert.assertEquals(1700, main.getCentury(1787));
        Assert.assertEquals(1700, main.getCentury(1700));
        Assert.assertEquals(2000, main.getCentury(2020));
    }

    @Test
    public void closestReferenceYearTest() {
        //exact zero year()s
        Assert.assertEquals(1900, main.getReferenceYear(1900).year());
        Assert.assertEquals(1928, main.getReferenceYear(1928).year());
        Assert.assertEquals(1956, main.getReferenceYear(1956).year());
        Assert.assertEquals(1984, main.getReferenceYear(1984).year());
        //exact incremented year()s
        Assert.assertEquals(1912, main.getReferenceYear(1912).year());
        Assert.assertEquals(1924, main.getReferenceYear(1924).year());
        Assert.assertEquals(1936, main.getReferenceYear(1936).year());
        Assert.assertEquals(1948, main.getReferenceYear(1948).year());
        Assert.assertEquals(1960, main.getReferenceYear(1960).year());
        Assert.assertEquals(1972, main.getReferenceYear(1972).year());
        Assert.assertEquals(1984, main.getReferenceYear(1984).year());
        Assert.assertEquals(1996, main.getReferenceYear(1996).year());
        //near
        Assert.assertEquals(1900, main.getReferenceYear(1901).year());
        Assert.assertEquals(1900, main.getReferenceYear(1902).year());
        Assert.assertEquals(1900, main.getReferenceYear(1903).year());
        //(...)
        Assert.assertEquals(1912, main.getReferenceYear(1913).year());
        Assert.assertEquals(1912, main.getReferenceYear(1914).year());
        Assert.assertEquals(1912, main.getReferenceYear(1915).year());
    }
}
