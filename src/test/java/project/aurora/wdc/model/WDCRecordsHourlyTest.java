package project.aurora.wdc.model;

import org.junit.Test;

import project.aurora.wdc.parser.WDCFileReader;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;


public class WDCRecordsHourlyTest {

    @Test
    public void testMonthlyRecords() throws FileNotFoundException {
        var kpDateSourceFile = new File(this.getClass().getResource("/wdc/kp2019.wdc").getFile());
        var wdcReader = new WDCFileReader(kpDateSourceFile);

        var monthly = wdcReader.getRecords();
        var october2March_kPs = monthly.octoberToMarch();
        var nightlyKP = october2March_kPs.getWinterNightlyKP();
        var highKP = nightlyKP.filterByKP(2.5);
        var onlyOctoberWithHighKP = highKP.filterByMonth(10);
        System.out.println(onlyOctoberWithHighKP.toString());
        var onlyOctoberWithHighKPExpected = monthly.onlyOctober().getWinterNightlyKP().filterByKP(2.5);

        assertEquals(onlyOctoberWithHighKP.size(), onlyOctoberWithHighKPExpected.size());

    }

}
