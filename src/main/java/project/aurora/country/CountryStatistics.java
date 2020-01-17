package project.aurora.country;

import project.aurora.utils.GPSCoordinates;
import project.aurora.utils.LocalDateUtils;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


public class CountryStatistics {

    private String countryName;
    private Map<GPSCoordinates, Set<LocalDate>> datesPerGPS;
    private Set<LocalDate> dates;
    private Integer totalDaysWithHighKP;

    public CountryStatistics(String countryName,
                             Map<GPSCoordinates, Set<LocalDate>> datesPerGPS,
                             Set<LocalDate> dates, Integer totalDaysWithHighKP) {
        this.countryName = countryName;
        this.datesPerGPS = datesPerGPS;
        this.dates = dates;
        this.totalDaysWithHighKP = totalDaysWithHighKP;
    }

    public Set<LocalDate> getDates() {
        return dates;
    }

    public LinkedHashMap<Month, List<LocalDate>> dividedByMonth() {
        return LocalDateUtils.divideByMonth(dates);
    }

    public Map<GPSCoordinates, Set<LocalDate>> getDatesPerGPS() {
        return datesPerGPS;
    }

    public Double getPercentage() {
        return 100 * (double) dates.size() / (double) totalDaysWithHighKP;
    }

    public String datesToString() {
        return dates.stream()
                .map(LocalDate::toString)
                .collect(Collectors.joining(", ", "[\n", "\n]"));
    }

    public String datesByMonthToString() {
        return LocalDateUtils.toString(dividedByMonth(), true);
    }

    public Integer getTotalDaysWithHighKP() {
        return totalDaysWithHighKP;
    }

    public String getCountryName() {
        return countryName;
    }

    // todo: implement it
    public String getTheBestMonth() {
        var entry = dividedByMonth().entrySet().stream().max(
                Comparator.comparingInt(m -> m.getValue().size())
        ).get();

        return entry.getKey().toString();
    }

    public String getTheBestLocation() {
        var entry = datesPerGPS.entrySet().stream().max(
                Comparator.comparingInt(x -> x.getValue().size())).get();

        return entry.getKey().name();
    }

}
