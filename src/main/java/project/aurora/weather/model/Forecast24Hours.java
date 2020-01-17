package project.aurora.weather.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast24Hours {
    private List<ForecastByHour> data;

    public List<ForecastByHour> perHour() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }
}
