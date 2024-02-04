import org.junit.Assert;
import org.junit.Test;
import sandromarques.dayoftheweek.DateUtils;
import sandromarques.dayoftheweek.DoomsdayAlgorithm;
import sandromarques.dayoftheweek.InputDate;
import sandromarques.dayoftheweek.Weekday;

public class DayTest {
    DoomsdayAlgorithm doomsdayAlgorithm = new DoomsdayAlgorithm();

    @Test
    public void getReferenceDoomsdayTest() {
        int nonLeapYear = 2001;
        Assert.assertEquals("14/3", doomsdayAlgorithm.getClosestDoomsday(7, 3, nonLeapYear).toString());
        Assert.assertEquals("28/2", doomsdayAlgorithm.getClosestDoomsday(6, 3, nonLeapYear).toString());
        Assert.assertEquals("14/3", doomsdayAlgorithm.getClosestDoomsday(15, 3, nonLeapYear).toString());
        Assert.assertEquals("10/10", doomsdayAlgorithm.getClosestDoomsday(10, 10, nonLeapYear).toString());
        Assert.assertEquals("6/6", doomsdayAlgorithm.getClosestDoomsday(15, 6, nonLeapYear).toString());
        Assert.assertEquals("4/7", doomsdayAlgorithm.getClosestDoomsday(31, 6, nonLeapYear).toString());
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
            Assert.assertTrue(doomsdayAlgorithm.isLeapYear(year));
        }
        for (int year = 1001; year <= 2048; year += 4) {
            Assert.assertFalse(doomsdayAlgorithm.isLeapYear(year));
        }
        Assert.assertFalse(doomsdayAlgorithm.isLeapYear(1900));
        Assert.assertFalse(doomsdayAlgorithm.isLeapYear(2100));
    }

    @Test
    public void doomsdayTest() {
        Assert.assertEquals(2, doomsdayAlgorithm.calculate(new InputDate(14, 3, 2000))); // TUESDAY (2)
        Assert.assertEquals(3, doomsdayAlgorithm.calculate(new InputDate(14, 3, 2001))); // WEDNESDAY (3)
        Assert.assertEquals(4, doomsdayAlgorithm.calculate(new InputDate(14, 3, 2002))); // THURSDAY (4)
        Assert.assertEquals(5, doomsdayAlgorithm.calculate(new InputDate(14, 3, 2003))); // FRIDAY (5)
        //leap year so instead of saturday (friday + 1) we add 2 days
        Assert.assertEquals(0, doomsdayAlgorithm.calculate(new InputDate(14, 3, 2004))); // SUNDAY (0)
        Assert.assertEquals(1, doomsdayAlgorithm.calculate(new InputDate(14, 3, 2005))); // MONDAY (1)
        //random years
        Assert.assertEquals(2, doomsdayAlgorithm.calculate(new InputDate(14, 3, 1972))); // TUESDAY (2)
        Assert.assertEquals(0, doomsdayAlgorithm.calculate(new InputDate(14, 3, 1976))); // SUNDAY (0)
        Assert.assertEquals(5, doomsdayAlgorithm.calculate(new InputDate(14, 3, 2031))); // FRIDAY (5)
        Assert.assertEquals(0, doomsdayAlgorithm.calculate(new InputDate(4, 2, 2024))); // SUNDAY (5)
        Assert.assertEquals(3, doomsdayAlgorithm.calculate(new InputDate(17, 1, 2024))); // WEDNESDAY (3)
    }

    @Test
    public void weekdaytest() {
        Assert.assertNull(Weekday.get(-1));
        Assert.assertEquals(Weekday.SUNDAY, Weekday.get(0));
        Assert.assertEquals(Weekday.MONDAY, Weekday.get(1));
        Assert.assertEquals(Weekday.TUESDAY, Weekday.get(2));
        Assert.assertEquals(Weekday.WEDNESDAY, Weekday.get(3));
        Assert.assertEquals(Weekday.THURSDAY, Weekday.get(4));
        Assert.assertEquals(Weekday.FRIDAY, Weekday.get(5));
        Assert.assertEquals(Weekday.SATURDAY, Weekday.get(6));
        Assert.assertNull(Weekday.get(7));
    }

    @Test
    public void dayTest() {
        //random dates
        Assert.assertEquals(3, doomsdayAlgorithm.calculate(new InputDate(14, 3, 2001))); // WEDNESDAY (3)
        Assert.assertEquals(6, doomsdayAlgorithm.calculate(new InputDate(24, 3, 2001))); // SUNDAY (6)
        Assert.assertEquals(1, doomsdayAlgorithm.calculate(new InputDate(24, 3, 2003))); // MONDAY (1)
        Assert.assertEquals(3, doomsdayAlgorithm.calculate(new InputDate(24, 3, 2004))); // WEDNESDAY (3)
        Assert.assertEquals(0, doomsdayAlgorithm.calculate(new InputDate(14, 4, 1985))); // SUNDAY (0)
        Assert.assertEquals(3, doomsdayAlgorithm.calculate(new InputDate(15, 5, 1985))); // WEDNESDAY (3)
        Assert.assertEquals(6, doomsdayAlgorithm.calculate(new InputDate(28, 9, 1985))); // SUNDAY (6)
        Assert.assertEquals(1, doomsdayAlgorithm.calculate(new InputDate(12, 1, 1970)));
        Assert.assertEquals(4, doomsdayAlgorithm.calculate(new InputDate(12, 1, 2023)));

        Assert.assertEquals(3, doomsdayAlgorithm.calculate(new InputDate(20, 1, 1971)));
        Assert.assertEquals(2, doomsdayAlgorithm.calculate(new InputDate(14, 10, 1980)));
        Assert.assertEquals(6, doomsdayAlgorithm.calculate(new InputDate(3, 4, 1982)));
        Assert.assertEquals(1, doomsdayAlgorithm.calculate(new InputDate(3, 2, 1992)));
        Assert.assertEquals(2, doomsdayAlgorithm.calculate(new InputDate(10, 4, 2001)));
        Assert.assertEquals(0, doomsdayAlgorithm.calculate(new InputDate(27, 6, 2010)));
        Assert.assertEquals(1, doomsdayAlgorithm.calculate(new InputDate(14, 2, 2011)));
        Assert.assertEquals(6, doomsdayAlgorithm.calculate(new InputDate(1, 1, 2011)));
        Assert.assertEquals(5, doomsdayAlgorithm.calculate(new InputDate(1, 1, 2010)));
        Assert.assertEquals(2, doomsdayAlgorithm.calculate(new InputDate(27, 12, 2011)));
        Assert.assertEquals(4, doomsdayAlgorithm.calculate(new InputDate(7, 6, 2012)));
        Assert.assertEquals(5, doomsdayAlgorithm.calculate(new InputDate(28, 7, 2017)));
        Assert.assertEquals(3, doomsdayAlgorithm.calculate(new InputDate(31, 1, 2018)));
        Assert.assertEquals(5, doomsdayAlgorithm.calculate(new InputDate(31, 5, 2024)));
        Assert.assertEquals(1, doomsdayAlgorithm.calculate(new InputDate(14, 10, 2024)));
        Assert.assertEquals(5, doomsdayAlgorithm.calculate(new InputDate(29, 11, 2024)));
        Assert.assertEquals(3, doomsdayAlgorithm.calculate(new InputDate(29, 7, 2043)));

        Assert.assertEquals(4, doomsdayAlgorithm.calculate(new InputDate(3, 3, 2011)));
        Assert.assertEquals(5, doomsdayAlgorithm.calculate(new InputDate(3, 3, 2012)));

        Assert.assertEquals(5, doomsdayAlgorithm.calculate(new InputDate(11, 3, 2044)));
        Assert.assertEquals(3, doomsdayAlgorithm.calculate(new InputDate(29, 3, 2084)));
        Assert.assertEquals(2, doomsdayAlgorithm.calculate(new InputDate(26, 4, 2095)));
        Assert.assertEquals(5, doomsdayAlgorithm.calculate(new InputDate(20, 5, 2067)));
        Assert.assertEquals(0, doomsdayAlgorithm.calculate(new InputDate(3, 8, 2064)));
        Assert.assertEquals(5, doomsdayAlgorithm.calculate(new InputDate(8, 8, 2092)));
        Assert.assertEquals(6, doomsdayAlgorithm.calculate(new InputDate(8, 9, 2096)));
        Assert.assertEquals(3, doomsdayAlgorithm.calculate(new InputDate(2, 11, 2095)));
        Assert.assertEquals(6, doomsdayAlgorithm.calculate(new InputDate(27, 12, 2087)));
    }


    @Test
    public void yearTest() {
        Assert.assertEquals(-1, doomsdayAlgorithm.getDoomsdayOfCentury(1699)); // < 1700

        Assert.assertEquals(0, doomsdayAlgorithm.getDoomsdayOfCentury(1700)); // SUNDAY (0)
        Assert.assertEquals(5, doomsdayAlgorithm.getDoomsdayOfCentury(1800)); // FRIDAY (5)
        Assert.assertEquals(3, doomsdayAlgorithm.getDoomsdayOfCentury(1900)); // WEDNESDAY (3)
        Assert.assertEquals(2, doomsdayAlgorithm.getDoomsdayOfCentury(2000)); // TUESDAY (2)

        Assert.assertEquals(0, doomsdayAlgorithm.getDoomsdayOfCentury(2100)); // SUNDAY (0)
        Assert.assertEquals(5, doomsdayAlgorithm.getDoomsdayOfCentury(2200)); // FRIDAY (5)
        Assert.assertEquals(3, doomsdayAlgorithm.getDoomsdayOfCentury(2300)); // WEDNESDAY (3)
        Assert.assertEquals(2, doomsdayAlgorithm.getDoomsdayOfCentury(2400)); // TUESDAY (2)
    }

    @Test
    public void centuryTest() {
        Assert.assertEquals(1600, doomsdayAlgorithm.getCentury(1699));
        Assert.assertEquals(1700, doomsdayAlgorithm.getCentury(1787));
        Assert.assertEquals(1700, doomsdayAlgorithm.getCentury(1700));
        Assert.assertEquals(2000, doomsdayAlgorithm.getCentury(2020));
    }

    @Test
    public void closestReferenceYear_incrementedYears_test() {
        int increment = 12;

        //exact incremented year()s (12 * N) from 0 to 96
        for (int i = 0; i < 8; i++) {
            int currIncrement = i * increment;
            DoomsdayAlgorithm.Pair pair = doomsdayAlgorithm.getReferenceYear(1900, 1900 + currIncrement);
            Assert.assertEquals(1900 + currIncrement, pair.year());
            Assert.assertEquals(i % 7, pair.inc());
        }
    }

    @Test
    public void closestReferenceYear_zeroYears_Test() {
        //exact zero year()s (28 * N) from 0 to 84
        int increment = 28;

        for (int i = 0; i < 4; i++) {
            int currIncrement = i * increment;
            DoomsdayAlgorithm.Pair pair = doomsdayAlgorithm.getReferenceYear(1900, 1900 + currIncrement);
            Assert.assertEquals(1900 + currIncrement, pair.year());
            Assert.assertEquals(0, pair.inc());
        }
    }

    @Test
    public void closestReferenceYear_returnsFloor_Test() {
        DoomsdayAlgorithm.Pair pair1 = doomsdayAlgorithm.getReferenceYear(1900, 1901);
        Assert.assertEquals(1900, pair1.year());
        Assert.assertEquals(0, pair1.inc());

        DoomsdayAlgorithm.Pair pair2 = doomsdayAlgorithm.getReferenceYear(1900, 1911);
        Assert.assertEquals(1900, pair2.year());
        Assert.assertEquals(0, pair2.inc());

        DoomsdayAlgorithm.Pair pair3 = doomsdayAlgorithm.getReferenceYear(1900, 1912);
        Assert.assertEquals(1912, pair3.year());
        Assert.assertEquals(1, pair3.inc());

        DoomsdayAlgorithm.Pair pair4 = doomsdayAlgorithm.getReferenceYear(1900, 1913);
        Assert.assertEquals(1912, pair4.year());
        Assert.assertEquals(1, pair4.inc());
    }

    @Test
    public void removeGroupsOf7Test() {
        for (int i = 0; i < 7; i++) {
            Assert.assertEquals(i, doomsdayAlgorithm.removeGroupsOf7(i));
            if (i != 0) {
                Assert.assertEquals(-i + 7, doomsdayAlgorithm.removeGroupsOf7(-i));
            }
        }
    }
}
