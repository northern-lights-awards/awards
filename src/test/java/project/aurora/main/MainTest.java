package project.aurora.main;

import org.junit.Before;
import org.junit.Test;
import project.aurora.country.Country;
import project.aurora.country.CountryAwards;
import project.aurora.country.CountryStatistics;
import project.aurora.wdc.parser.WDCFileReader;
import project.aurora.weather.WeatherService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTest {

    Country norway;
    Country finland;
    Country sweden;
    Country iceland;

    WDCFileReader wdcReader;
    WeatherService weatherService;

    @Before
    public void beforeMethod() throws IOException {
        org.junit.Assume.assumeFalse(System.getenv("DARKSKY_KEY") == null);
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
        weatherService = new WeatherService(System.getenv("DARKSKY_KEY"));
    }

    private List<Country> getListOfCountries() {
        return Arrays.asList(norway, iceland, sweden, finland);
    }

    public String resourceFileToString(Class<?> testClass, String resourceName) throws IOException {
        return new String(
                Files.readAllBytes(
                        Paths.get(testClass.getResource("/" + resourceName).getPath())
                )
        );
    }

    @Test
    public void testMain() {
        var countries = new ArrayList<CountryStatistics>();
        var monthsRange = wdcReader.getRecords().octoberToMarch()
                .getWinterNightlyKP()
                .sortedByDate();

        getListOfCountries().forEach(country -> {
            var countryStatistics = country.getSuccessfulDates(monthsRange, weatherService);
            System.out.println(String.format("\n%s had %s days with observable northern lights, " +
                            "%s%s of total dates", country.getName(),
                    countryStatistics.getDates().size(), countryStatistics.getPercentage(), "%"));
            System.out.println(
                    String.format("per-month activity (month to a number of dates with higher kP): %s\n",
                            countryStatistics.datesByMonthToString()
                    )
            );
            System.out.println(String.format("%s\n", countryStatistics.getTheBestLocation()));

            countries.add(countryStatistics);
        });

        var countryAwards = new CountryAwards(countries);
        System.out.println(countryAwards.getTheBestCountry());

    }

}
