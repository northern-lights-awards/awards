package project.aurora.weather;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;

import project.aurora.utils.HTTPClient;
import project.aurora.weather.model.ForecastResponse;
import project.aurora.utils.GPSCoordinates;


public class WeatherService extends HTTPClient {

    /*
    * API request: https://api.darksky.net/forecast/[key]/[latitude],[longitude],[time]?exclude=currently,daily,flags
    * - key: DarkSky API key
    * - latitude: GPS latitude
    * - longitude: GPS longitude
    * - time: [YYYY]-[MM]-[DD]T[HH]:[MM]:[SS][timezone]
    * */
    protected String apiKey;

    public WeatherService(String weatherAPIKey) {
        apiKey = weatherAPIKey;
    }

    public ForecastResponse getForecastByDateAndGPS(LocalDate date, GPSCoordinates coordinates, ZoneId zoneID){
        try {
            return ForecastResponse.fromString(date, doGet(new URI(
                    "https://api.darksky.net/forecast/" +
                            apiKey + "/" + coordinates.toString() + "," +
                            date.atStartOfDay(zoneID).toEpochSecond() +
                            "?exclude=currently,daily,flags"
            )));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

}
