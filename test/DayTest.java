import org.junit.Assert;
import org.junit.Test;
import sandromarques.dayoftheweek.DayOfTheWeek;

public class DayTest {
    DayOfTheWeek main = new DayOfTheWeek(false);

    @Test
    public void getReferenceDoomsdayTest() {
        Assert.assertEquals("14/3",main.getReferenceDayMonth(14,3));
        Assert.assertEquals("14/3",main.getReferenceDayMonth(5,3));
        Assert.assertEquals("14/3",main.getReferenceDayMonth(15,3));
        Assert.assertEquals("10/10",main.getReferenceDayMonth(10,10));
        Assert.assertEquals("6/6",main.getReferenceDayMonth(15,6));
        Assert.assertEquals("4/7",main.getReferenceDayMonth(31,6));
    }

    @Test
    public void validDateInputTest() {
        Assert.assertTrue(main.isDateValid("10/10/2020"));
        //TODO: Assert.assertTrue(main.isDateValid("1/1/2020"));
        Assert.assertTrue(main.isDateValid("01/01/2020"));

        Assert.assertFalse(main.isDateValid("31/02/2020")); // date doesn't exist but it has a valid format
        Assert.assertFalse(main.isDateValid("10-10-2020"));
        Assert.assertFalse(main.isDateValid("10/10/202."));
        Assert.assertFalse(main.isDateValid("10/.0/2020"));
        Assert.assertFalse(main.isDateValid(".0/10/2020"));
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
        Assert.assertEquals(2, main.calculate(14, 3, 2000)); // TUESDAY (2)
        Assert.assertEquals(3, main.calculate(14, 3, 2001)); // WEDNESDAY (3)
        Assert.assertEquals(4, main.calculate(14, 3, 2002)); // THURSDAY (4)
        Assert.assertEquals(5, main.calculate(14, 3, 2003)); // FRIDAY (5)
        //leap year so instead of saturday (friday + 1) we add 2 days
        Assert.assertEquals(0, main.calculate(14, 3, 2004)); // SUNDAY (0)
        Assert.assertEquals(1, main.calculate(14, 3, 2005)); // MONDAY (1)
        //random years
        Assert.assertEquals(2, main.calculate(14, 3, 1972)); // TUESDAY (2)
        Assert.assertEquals(0, main.calculate(14, 3, 1976)); // SUNDAY (0)
        Assert.assertEquals(5, main.calculate(14, 3, 2031)); // FRIDAY (5)
    }

    @Test
    public void dayTest() {
        //random dates
        Assert.assertEquals(3, main.calculate(14, 3, 2001)); // WEDNESDAY (3)
        Assert.assertEquals(6, main.calculate(24, 3, 2001)); // SATURDAY (6)
        Assert.assertEquals(1, main.calculate(24, 3, 2003)); // MONDAY (1)
        Assert.assertEquals(3, main.calculate(24, 3, 2004)); // WEDNESDAY (3)
        Assert.assertEquals(0, main.calculate(14, 4, 1985)); // SUNDAY (0)
        Assert.assertEquals(3, main.calculate(15, 5, 1985)); // WEDNESDAY (3)
        Assert.assertEquals(6, main.calculate(28, 9, 1985)); // SUNDAY (6)
        Assert.assertEquals(1, main.calculate(12, 1, 1970));
        Assert.assertEquals(4, main.calculate(12, 1, 2023));

        Assert.assertEquals(3,main.calculate(20,1,1971));
        Assert.assertEquals(2,main.calculate(14,10,1980));
        Assert.assertEquals(6,main.calculate(3,4,1982));
        //TODO: Assert.assertEquals(1,main.calculate(3,2,1992));
        Assert.assertEquals(2,main.calculate(10,4,2001));
        Assert.assertEquals(0,main.calculate(27,6,2010));
        //TODO: Assert.assertEquals(2,main.calculate(14,2,2011));
        Assert.assertEquals(2,main.calculate(27,12,2011));
        Assert.assertEquals(4,main.calculate(7,6,2012));
        Assert.assertEquals(5,main.calculate(28,7,2017));
        /*
        More random dates from random.org to add to testing
        Assert.assertEquals(,main.calculate(31,1,2018));
        Assert.assertEquals(,main.calculate(31,5,2024));
        Assert.assertEquals(,main.calculate(14,10,2024));
        Assert.assertEquals(,main.calculate(29,11,2024));
        Assert.assertEquals(,main.calculate(29,7,2043));
        Assert.assertEquals(,main.calculate(11,3,2044));
        Assert.assertEquals(,main.calculate(3,08,2064));
        Assert.assertEquals(,main.calculate(20,5,2067));
        Assert.assertEquals(,main.calculate(4,3,2080));
        Assert.assertEquals(,main.calculate(27,3,2084));
        Assert.assertEquals(,main.calculate(27,12,2087));
        Assert.assertEquals(,main.calculate(08,08,2092));
        Assert.assertEquals(,main.calculate(26,4,2095));
        Assert.assertEquals(,main.calculate(2,11,2095));
        Assert.assertEquals(,main.calculate(08,09,2096));
         */
    }

    @Test
    public void yearTest() {
        Assert.assertEquals(-1, main.getNumberFromYear(1699)); // < 1700

        Assert.assertEquals(0, main.getNumberFromYear(1700)); // SUNDAY (0)
        Assert.assertEquals(5, main.getNumberFromYear(1800)); // FRIDAY (5)
        Assert.assertEquals(3, main.getNumberFromYear(1900)); // WEDNESDAY (3)
        Assert.assertEquals(2, main.getNumberFromYear(2000)); // TUESDAY (2)

        Assert.assertEquals(0, main.getNumberFromYear(2100)); // SUNDAY (0)
        Assert.assertEquals(5, main.getNumberFromYear(2200)); // FRIDAY (5)
        Assert.assertEquals(3, main.getNumberFromYear(2300)); // WEDNESDAY (3)
        Assert.assertEquals(2, main.getNumberFromYear(2400)); // TUESDAY (2)
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
