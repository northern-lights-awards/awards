package project.aurora.wdc.model;

import java.time.LocalDate;
import java.util.Map;

public class WDCRecordsHourlyNamed extends WDCRecordsHourly {

    String monthName;

    public WDCRecordsHourlyNamed(Map<LocalDate, WDCRecord> r, String monthName) {
        super(r);
        this.monthName = monthName;
    }

    public String getName() {
        return monthName;
    }

}
