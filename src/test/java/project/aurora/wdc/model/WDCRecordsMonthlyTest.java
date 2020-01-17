package project.aurora.wdc.model;

import org.junit.Test;
import project.aurora.wdc.parser.WDCFileReader;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;


public class WDCRecordsMonthlyTest {

    @Test
    public void testMonthlyRecords() throws FileNotFoundException {
        var kpDateSourceFile = new File(this.getClass().getResource("/wdc/kp2019.wdc").getFile());
        var wdcReader = new WDCFileReader(kpDateSourceFile);

        var monthly = wdcReader.getRecords();
        assertNotNull(monthly);
        assertTrue(monthly.size() < 365);
    }

    @Test
    public void testPerMonth() throws FileNotFoundException {
        var kpDateSourceFile = new File(this.getClass().getResource("/wdc/kp2019.wdc").getFile());
        var wdcReader = new WDCFileReader(kpDateSourceFile);

        var monthly = wdcReader.getRecords();

        var size = monthly.onlyOctober().size() +
                monthly.onlyNovember().size() +
                monthly.onlyDecember().size() +
                monthly.onlyJanuary().size() +
                monthly.onlyFebruary().size() +
                monthly.onlyMarch().size();

        assertEquals(monthly.octoberToMarch().size(), size);
    }
}
