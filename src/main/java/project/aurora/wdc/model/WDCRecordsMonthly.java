package project.aurora.wdc.model;

import project.aurora.wdc.util.WDCRecordsFilter;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class WDCRecordsMonthly {

    protected Map<LocalDate, WDCRecord> records;

    public String toString() {
        return records.keySet().stream()
                .map(key -> key + ": " + records.get(key).toString())
                .collect(Collectors.joining(", \n", "{", "}\n"));
    }

    // Purpose of "monthly" records to filter out summer months and provide per-month statistics.
    // Internet claims that people often see aurora:
    //     - from October to March
    //     - April and September rarely
    public WDCRecordsMonthly(Map<LocalDate, WDCRecord> r) {
        // we don't care about summer months
        records = r.entrySet().stream().parallel()
                .filter(
                        x -> x.getKey().getMonthValue() < 4 || x.getKey().getMonthValue() > 9
                ).collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
                );
    }

    public int size() {
        return records.size();
    }

    private WDCRecordsHourly getRange(Integer startingMonth, Integer endMonth) {
        return new WDCRecordsHourly(records.entrySet().parallelStream()
                .filter(
                        x -> x.getKey().getMonthValue() >= startingMonth ||
                                x.getKey().getMonthValue() <= endMonth
                )
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
                ));

    }
    public WDCRecordsHourly septemberToApril() {
        return getRange(9, 4);
    }

    public WDCRecordsHourly octoberToMarch() {
        return getRange(10, 3);
    }

    private WDCRecordsHourlyNamed byMonth(Integer month) {
        month += 1;
        var monthName = Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
        return new WDCRecordsHourlyNamed(
                WDCRecordsFilter.onlyCertainMonth(records, month), monthName
        );
    }

    public WDCRecordsHourlyNamed onlySeptember() {
        return byMonth(Calendar.SEPTEMBER);
    }

    public WDCRecordsHourlyNamed onlyOctober() {
        return byMonth(Calendar.OCTOBER);
    }

    public WDCRecordsHourlyNamed onlyNovember() {
        return byMonth(Calendar.NOVEMBER);
    }

    public WDCRecordsHourlyNamed onlyDecember() {
        return byMonth(Calendar.DECEMBER);
    }

    public WDCRecordsHourlyNamed onlyJanuary() {
        return byMonth(Calendar.JANUARY);
    }

    public WDCRecordsHourlyNamed onlyFebruary() {
        return byMonth(Calendar.FEBRUARY);
    }

    public WDCRecordsHourlyNamed onlyMarch() {
        return byMonth(Calendar.MARCH);
    }

    public WDCRecordsHourlyNamed onlyApril() {
        return byMonth(Calendar.APRIL);
    }

}
