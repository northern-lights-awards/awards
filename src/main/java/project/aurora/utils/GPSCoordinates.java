package project.aurora.utils;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GPSCoordinates {

    private Double latitude;
    private Double longitude;
    private String description;

    // noop
    public GPSCoordinates() {}

    public GPSCoordinates(Double latitude, Double longitude) {
        new GPSCoordinates(latitude, longitude, "<unknown>");
    }

    public GPSCoordinates(Double latitude, Double longitude, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public String toHTTPParameters() {
        return String.format("lat=%s&lng=%s", Double.toString(latitude), Double.toString(longitude));
    }

    public String name() {
        return String.format("%s [%s]", description, toString());
    }

    public String toString() {
        return Double.toString(latitude) + "," + Double.toString(longitude);
    }
}
