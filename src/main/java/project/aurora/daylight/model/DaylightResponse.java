package project.aurora.daylight.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DaylightResponse {

    private DaylightDetails results;
    private String status;

    public String  getDaylightLength() {
        return results.getDayLength();
    }

    public Long getDaylightLengthAsLong() {
        var parts = getDaylightLength().split(":");
        var h = Long.parseLong(parts[0]) * 60;
        var m = Long.parseLong(parts[1]);
        var s = Long.parseLong(parts[2]) > 30 ? 1 : 0;

        return h + m + s;
    }
}
