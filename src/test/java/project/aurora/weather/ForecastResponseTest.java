package project.aurora.weather;

import org.junit.Test;
import project.aurora.wdc.timeframe.Timeframe;
import project.aurora.wdc.util.TimeframeUtils;
import project.aurora.weather.model.ForecastResponse;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;

import static org.junit.Assert.*;

public class ForecastResponseTest {

    @Test
    public void testCanParseJSON() throws IOException {
        var jsonFile = new String(Files.readAllBytes(
                Paths.get(this.getClass().getResource("/weather.json").getPath())
            )
        );
        var date = Instant.ofEpochSecond(1569888000).
                atZone(ZoneId.of("Atlantic/Reykjavik")).
                toLocalDate();
        var forecast = ForecastResponse.fromString(date, jsonFile);

        assertNotNull(forecast);
        assertEquals(forecast.getHourly().size(), 24);
    }

    @Test
    public void testFilterHours() throws IOException {
        var jsonFile = new String(Files.readAllBytes(
                Paths.get(this.getClass().getResource("/weather.json").getPath())
            )
        );

        var date = Instant.ofEpochSecond(1569888000).
                atZone(ZoneId.of("Atlantic/Reykjavik")).
                toLocalDate();
        var forecast = ForecastResponse.fromString(date, jsonFile);

        var f = forecast.filterByTimeframes(Collections.singletonList(Timeframe.TIME03));
        System.out.println(f.size());
        f.forEach(x -> {
            System.out.println(x.getLocalDateTime(forecast.getTimezone()).toString());
        });

        assertEquals(TimeframeUtils.toHours(Collections.singletonList(Timeframe.TIME03)).size(), f.size());
    }

}
