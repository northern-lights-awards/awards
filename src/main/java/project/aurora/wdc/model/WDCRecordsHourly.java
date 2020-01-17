package project.aurora.wdc.model;

import project.aurora.wdc.timeframe.Timeframe;
import project.aurora.wdc.util.WDCRecordsFilter;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class WDCRecordsHourly {

    private Map<LocalDate, WDCRecord> records;

    public String toString() {
        return records.keySet().stream()
                .map(key -> key + " : " + records.get(key).toString())
                .collect(Collectors.joining(", \n", "{", "}\n"));
    }

    public int size() {
        return records.size();
    }

    public WDCRecordsHourly(Map<LocalDate, WDCRecord> r) {
        records = r;
    }

    public WDCRecordsHourly getWinterNightlyKP() {
        return new WDCRecordsHourly(
                WDCRecordsFilter.filterByTimeframe(
                        records,
                        Arrays.asList(
                                Timeframe.TIME03,
                                Timeframe.TIME36,
                                Timeframe.TIME2124
                        )
                )
        );
    }

    public WDCRecordsHourly kPat03() {
        return new WDCRecordsHourly(WDCRecordsFilter.filterByTimeframe(
                records, Collections.singletonList(Timeframe.TIME03))
        );
    }

    public WDCRecordsHourly kPat36() {
        return new WDCRecordsHourly(WDCRecordsFilter.filterByTimeframe(
                records, Collections.singletonList(Timeframe.TIME36))
        );
    }

    public WDCRecordsHourly kPat2124() {
        return new WDCRecordsHourly(WDCRecordsFilter.filterByTimeframe(
                records, Collections.singletonList(Timeframe.TIME2124))
        );
    }

    public WDCRecordsHourly filterByKP(Double greaterThan) {
        return new WDCRecordsHourly(
                WDCRecordsFilter.removeQuiteDays(
                        WDCRecordsFilter.filterByKPValue(records, greaterThan)
                )
        );
    }

    public WDCRecordsHourly filterByMonth(Integer month) {
        return new WDCRecordsHourly(WDCRecordsFilter.onlyCertainMonth(records, month));
    }

    public WDCRecordsHourly reverseSortedByDate() {
        return new WDCRecordsHourly(WDCRecordsFilter.sortedByDate(records, true));
    }

    public WDCRecordsHourly sortedByDate() {
        return new WDCRecordsHourly(WDCRecordsFilter.sortedByDate(records, false));
    }

    public Set<Map.Entry<LocalDate, WDCRecord>> toEntries() {
        return records.entrySet();
    }

    public Set<LocalDate> getListOfDates() {
        return records.keySet();
    }

}
