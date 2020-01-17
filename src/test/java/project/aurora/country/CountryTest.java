package project.aurora.country;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class CountryTest {

    public String resourceFileToString(Class<?> testClass, String resourceName) throws IOException {
        return new String(
                Files.readAllBytes(
                        Paths.get(testClass.getResource("/" + resourceName).getPath())
                )
        );
    }

    @Test
    public void testCanCreateCountryFromJSON() throws IOException {
        var norway = Country.fromString(
                resourceFileToString(this.getClass(), "countries/norway.json")
        );

        assertEquals("Norway", norway.getName());
        assertEquals("Europe/Oslo", norway.getZoneID().toString());
        assertEquals(Double.valueOf(2.5), norway.getMinimumVisibleKP());
        assertEquals(17, norway.getGPSCoordinates().size());
    }
}
