package project.aurora.weather.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import project.aurora.utils.GPSCoordinates;
import project.aurora.wdc.model.WDCRecord;
import project.aurora.wdc.timeframe.Timeframe;
import project.aurora.wdc.util.TimeframeUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse {

    @JsonIgnore
    private LocalDate date;

    private String timezone;
    private Forecast24Hours hourly;
    private Double latitude;
    private Double longitude;

    public GPSCoordinates getGPSCoordinates() {
        return new GPSCoordinates(latitude, longitude);
    }

    public static ForecastResponse fromString(LocalDate date, String respString) throws JsonProcessingException {
        return new ObjectMapper().
                readValue(respString, ForecastResponse.class).
                setDate(date);
    }

    private ForecastResponse setDate(LocalDate d) {
        date = d;
        return this;
    }

    private LocalDate getDate() {
        return date;
    }

    public ZoneId getTimezone() {
        return ZoneId.of(timezone);
    }

    public List<ForecastByHour> getHourly() {
        if (hourly == null) {
            hourly = new Forecast24Hours();
        }
        return hourly.perHour();
    }

    public List<ForecastByHour> filterByTimeframes(List<Timeframe> tfs) {
        var tmp = TimeframeUtils.toUNIXepoch(getDate(), getTimezone(), tfs);
        var f = getHourly().parallelStream().filter(
                x -> tmp.contains(x.getTime())
        ).collect(Collectors.toList());
        return f;
    }

    public Boolean checkForecast(WDCRecord wdc) {
        var hoursFromTimeframes = wdc.getTimeframes();
//        System.out.println(String.format("Hours to filter: %s", hoursFromTimeframes.size()));
        var hourly = filterByTimeframes(hoursFromTimeframes);
//        System.out.println(String.format("Size of the filtered hourly forecast: %s", hourly.size()));

        var finale = hourly.parallelStream().filter(ForecastByHour::isSuitable).collect(Collectors.toList());
//        System.out.println(String.format("Hits by hours: %s", finale.toString()));
        // if the weather of 50% of nighttime hours were fine - the day is good
        return finale.size() * 2 >= hourly.size();
    }

}
