package project.aurora.wdc.parser;

import project.aurora.wdc.model.WDCRecordsMonthly;
import project.aurora.wdc.model.WDCRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;


public class WDCFileReader {

    protected WDCRecordsMonthly records;

    public WDCFileReader(File wdcFile) throws FileNotFoundException {
        var r = new HashMap<LocalDate, WDCRecord>();
        var wdcScanner = new Scanner(wdcFile);
        while (wdcScanner.hasNextLine()) {
            var kPRecord = wdcScanner.nextLine();
            WDCRecordParser.toDate(kPRecord);
            r.put(
                WDCRecordParser.toDate(kPRecord),
                new WDCRecord(kPRecord)
            );
        }
        records = new WDCRecordsMonthly(r);
    }

    public WDCRecordsMonthly getRecords() {
        return records;
    }
}
