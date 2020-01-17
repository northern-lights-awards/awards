package project.aurora.country;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.aurora.utils.GPSCoordinates;
import project.aurora.wdc.model.WDCRecord;
import project.aurora.wdc.model.WDCRecordsHourly;
import project.aurora.weather.WeatherService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    private String name;
    private List<GPSCoordinates> gpsCoordinates;
    private Double minimumVisibleKP;

    @JsonIgnore
    private ZoneId zoneID;

    @JsonProperty("zoneID")
    public Country setZoneID(String zoneID) {
        this.zoneID = ZoneId.of(zoneID);
        return this;
    }

    public ZoneId getZoneID() {
        return zoneID;
    }
    public String getName() {
        return name;
    }
    public Double getMinimumVisibleKP() {
        return minimumVisibleKP;
    }
    public List<GPSCoordinates> getGPSCoordinates() {
        return gpsCoordinates;
    }

    public static Country fromString(String respString) throws JsonProcessingException {
        return new ObjectMapper().readValue(respString, Country.class);
    }

    public Boolean dayIsSuitable(GPSCoordinates coordinates, LocalDate date, WDCRecord wdc, WeatherService weatherService) {
        return weatherService.getForecastByDateAndGPS(date, coordinates, zoneID).checkForecast(wdc);
    }

    public CountryStatistics getSuccessfulDates(WDCRecordsHourly wdcHourly, WeatherService weatherService) {
        var dates = new HashSet<LocalDate>();
        var datesPerGPS = new HashMap<GPSCoordinates, Set<LocalDate>>();
        var filtered = wdcHourly.filterByKP(minimumVisibleKP);
        var filteredSize = filtered.size();

        gpsCoordinates.parallelStream().forEach(coordinates -> {
            var subLocalDates = new HashSet<LocalDate>();
            filtered.toEntries().parallelStream().forEachOrdered(wdcEntry -> {
                var date = wdcEntry.getKey();
                if (dayIsSuitable(coordinates, date, wdcEntry.getValue(), weatherService)) {
                    // System.out.println(String.format("Day %s was successful", date.toString()));
                    subLocalDates.add(date);
                }
            });
            dates.addAll(subLocalDates);
            datesPerGPS.put(coordinates, subLocalDates);
        });

        return new CountryStatistics(
                name, datesPerGPS, dates, filteredSize
        );
    }

}
