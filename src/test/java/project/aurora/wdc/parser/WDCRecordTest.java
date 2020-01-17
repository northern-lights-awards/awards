package project.aurora.wdc.parser;

import org.junit.Test;
import project.aurora.wdc.model.WDCRecord;
import project.aurora.wdc.timeframe.Timeframe;

import java.io.*;
import java.util.Collections;
import java.util.Scanner;

import static org.junit.Assert.*;


public class WDCRecordTest
{

    @Test
    public void testCanParseSingleKPValue() throws IOException {
        try {
            var kpDateSourceFile = new File(this.getClass().getResource("/wdc/kp2019.wdc").getFile());
            var kpDateSource = new Scanner(kpDateSourceFile);
            var singleRecord = kpDateSource.nextLine();

            var hourlyKP = WDCRecordParser.read(singleRecord);
            var date = WDCRecordParser.toDate(singleRecord);

            assertNotNull(hourlyKP);
            assertEquals(8, hourlyKP.size());
            assertNotNull(date);


        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    public void testCanGetHighestKP() throws IOException {
        try {
            var kpDateSourceFile = new File(this.getClass().getResource("/wdc/kp2019.wdc").getFile());
            var kpDateSource = new Scanner(kpDateSourceFile);
            var singleRecord = kpDateSource.nextLine();

            var wdc = new WDCRecord(singleRecord);

            assertNotNull(wdc);

            var highestKPEntry = wdc.getHighestKPThroughTheDay();

            assertNotNull(highestKPEntry);

            var highestKP = highestKPEntry.getValue();

            assertNotNull(highestKP);
            assertTrue(highestKP > 0);


        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    public void testGetMultipleKPByTimeframe() throws FileNotFoundException {

        var kpDateSourceFile = new File(this.getClass().getResource("/wdc/kp2019.wdc").getFile());
        var kpDateSource = new Scanner(kpDateSourceFile);
        var singleRecord = kpDateSource.nextLine();

        var wdc = new WDCRecord(singleRecord);
        var newWdc = wdc.getMultipleKPByTimeframe(Collections.singletonList(Timeframe.TIME03));
        assertNotEquals(wdc.length(), newWdc.length());
        assertEquals(1, newWdc.length());
    }
}
