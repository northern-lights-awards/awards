package project.aurora.wdc.timeframe;

import org.junit.Test;

import project.aurora.wdc.util.TimeframeUtils;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TimeframeUtilsTest {

    @Test
    public void testTimeframeConverter() {
        var actual = TimeframeUtils.toHours(Arrays.asList(
                Timeframe.TIME2124, Timeframe.TIME36, Timeframe.TIME03
        ));
        assertTrue(actual.containsAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 21, 22, 23, 24)));
    }

    @Test
    public void testIsNotNightTime() {
        assertFalse(TimeframeUtils.isNightTime(Timeframe.TIME69));
    }

    @Test
    public void testIsNightTime() {
        assertTrue(TimeframeUtils.isNightTime(Timeframe.TIME03));
    }

}
