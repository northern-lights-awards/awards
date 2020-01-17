package project.aurora.weather.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastByHour {
    /*
        * {
                "time": 1569970800,
                "summary": "Mostly Cloudy",
                "precipProbability": 0.06,
                "precipType": "rain",
                "cloudCover": 0.78,
                "visibility": 10,
           }
    */
    private Long time;
    private String summary;
    private Double precipProbability;
    private Double cloudCover;
    private String precipType;
    private Long visibility;

    public Long getTime() {
        return time;
    }

    public LocalDate getLocalDate(ZoneId zoneID) {
        return Instant.ofEpochSecond(time).
                atZone(zoneID).
                toLocalDate();
    }

    public LocalDateTime getLocalDateTime(ZoneId zoneID) {
        return Instant.ofEpochSecond(time).atZone(zoneID).toLocalDateTime();
    }

    public Double getPrecipitationProbability() {
        return precipProbability;
    }

    public Double getCloudCover() {
        return cloudCover;
    }

    public String summary() {
        return summary;
    }

    public Boolean isSuitable() {
        return (cloudCover != null && cloudCover <= 0.3) &
                (precipProbability != null && precipProbability < 0.1);
    }

}
