package project.aurora.daylight;

import com.fasterxml.jackson.databind.ObjectMapper;
import project.aurora.daylight.model.DaylightResponse;
import project.aurora.utils.GPSCoordinates;
import project.aurora.utils.HTTPClient;

import java.net.URI;
import java.time.LocalDate;


public class GPSBoundService extends HTTPClient {

    /* Daylight statistics: https://sunrise-sunset.org/api
    *  HTTP Request format:
    * - https://api.sunrise-sunset.org/json?lat=<LATITUDE>&lng=<LONGITUDE>&date=<DATE>
    * Parameters:
    * - LATITUDE: double
    * - LONGITUDE: double
    * - DATE: LocalDate (YYYY-MM-DD)
    * */

    public Long getDaylightLength(GPSCoordinates gpsCoordinates, LocalDate date) throws Exception {
        var response = doGet(new URI(
                String.format("https://api.sunrise-sunset.org/json?%s&date=%s",
                        gpsCoordinates.toHTTPParameters(),
                        date.toString()
                )
        ));
        var daylightInfo = new ObjectMapper().readValue(response, DaylightResponse.class);
        return daylightInfo.getDaylightLengthAsLong();
    }

}
