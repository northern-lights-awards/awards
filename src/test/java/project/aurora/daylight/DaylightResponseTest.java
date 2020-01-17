package project.aurora.daylight;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import project.aurora.daylight.model.DaylightResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class DaylightResponseTest {

    @Test
    public void canParseTimestampToMinutesAsLong() throws IOException {
        var jsonFile = new String(Files.readAllBytes(
                Paths.get(this.getClass().getResource("/daylight.json").getPath())
            )
        );

        var daylightInfo = new ObjectMapper().readValue(jsonFile, DaylightResponse.class);
        daylightInfo.getDaylightLength();

        // HH:mm:ss --> 4:22:22 --> 262 minutes
        assertEquals(Long.valueOf(262), daylightInfo.getDaylightLengthAsLong());
    }

}
