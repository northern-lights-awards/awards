package project.aurora.daylight.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DaylightDetails {

    @JsonProperty("day_length")
    private String dayLength;

    public String getDayLength() {
        return dayLength;
    }
}
