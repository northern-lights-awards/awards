package project.aurora.main;

import org.junit.Before;
import org.junit.Test;
import project.aurora.country.Country;
import project.aurora.utils.LocalDateUtils;
import project.aurora.wdc.model.WDCRecordsHourly;
import project.aurora.wdc.parser.WDCFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

public class SeasonRequestCalculationsTest {

    Country norway;
    Country finland;
    Country sweden;
    Country iceland;

    WDCFileReader wdcReader;

    public String resourceFileToString(Class<?> testClass, String resourceName) throws IOException {
        return new String(
                Files.readAllBytes(
                        Paths.get(testClass.getResource("/" + resourceName).getPath())
                )
        );
    }

    private List<Country> getListOfCountries() {
        return Arrays.asList(norway, iceland, sweden, finland);
    }

    @Before
    public void beforeTest() throws IOException {
        System.out.println("This is not a real test just an attempt to understand " +
                "how many requests you'd need to execute against weather service.\n");

        norway = Country.fromString(resourceFileToString(
                this.getClass(), "countries/norway.json")
        );
        sweden = Country.fromString(resourceFileToString(
                this.getClass(),"countries/sweden.json")
        );
        iceland = Country.fromString(resourceFileToString(
                this.getClass(),"countries/iceland.json")
        );
        finland = Country.fromString(resourceFileToString(
                this.getClass(),"countries/finland.json")
        );

        wdcReader = new WDCFileReader(
                new File(this.getClass().getResource("/wdc/kp2019.wdc").getFile())
        );
    }


    @Test
    public void canCalculateNumberOfRequestsRequired() {
        var monthsRange = wdcReader.getRecords().septemberToApril()
                .getWinterNightlyKP()
                .sortedByDate();
        var counter = new LongAdder();

        getListOfCountries().forEach(c -> {
            counter.add(getDaysForCountry(monthsRange, c));
        });

        System.out.println(counter.sumThenReset());
    }

    private int getDaysForCountry(WDCRecordsHourly monthsRange, Country c) {
        var kPs = monthsRange.filterByKP(c.getMinimumVisibleKP());
        System.out.println(String.format("country: %s\ndates to check the weather: %s",
                c.getName(), kPs.size()));
        System.out.println(String.format(
                "per-month activity (month to a number of dates with higher kP): %s",
                LocalDateUtils.toString(LocalDateUtils.divideByMonth(kPs.getListOfDates()), true))
        );
        System.out.println(String.format("GPS locations to check: %s\n",
                c.getGPSCoordinates().size()));
        return kPs.size() * c.getGPSCoordinates().size();
    }

    @Test
    public void canCalculateRequestsPerMonth() {
        var monthsRange = wdcReader.getRecords();
        var counter = new LongAdder();

        Stream.of(
                monthsRange.onlySeptember(), monthsRange.onlyOctober(),
                monthsRange.onlyNovember(), monthsRange.onlyDecember(),
                monthsRange.onlyJanuary(), monthsRange.onlyFebruary(),
                monthsRange.onlyMarch(), monthsRange.onlyApril()
        ).forEach(month -> {
            System.out.println(month.getName());
            getListOfCountries().forEach(c -> {
                    var totalDays = getDaysForCountry(month.getWinterNightlyKP(), c);
                    System.out.println(String.format(
                            "total number of weather requests: %s\n",totalDays)
                    );
                    counter.add(totalDays);
                }
            );
            System.out.println(String.format("total requests per month: %s", counter.sumThenReset()));
            System.out.println("\n\n");
        });
    }

}
