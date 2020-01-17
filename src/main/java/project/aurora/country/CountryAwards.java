package project.aurora.country;

import java.util.Comparator;
import java.util.List;

public class CountryAwards {

    CountryStatistics winner;

    public CountryAwards(List<CountryStatistics> countryStatisticsList) {
        this.winner = getCountry(countryStatisticsList);
    }

    private CountryStatistics getCountry(List<CountryStatistics> countryStatisticsList) {
        return countryStatisticsList.stream().max(
                Comparator.comparingDouble(CountryStatistics::getPercentage)
        ).get();
    }

    public String getTheBestCountry() {
        return String.format(
          "And the Winner is: %s, Congrats!\n" +
          "Chances to see the Aurora were: %s\n" +
          "With total number of clear sky nights vs high kP nights: %s vs %s\n" +
          "The best month: %s\n" +
          "The best location: %s\n",
                winner.getCountryName(), winner.getPercentage(),
                winner.getDates().size(), winner.getTotalDaysWithHighKP(),
                winner.getTheBestMonth(), winner.getTheBestLocation()
        );
    }

}
