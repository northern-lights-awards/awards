package project.aurora.wdc.util;

import project.aurora.wdc.model.WDCRecord;
import project.aurora.wdc.timeframe.Timeframe;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class WDCRecordsFilter {

    // provides a mapping of 3-hour frame with the corresponding kP value that is higher than requested
    public static Map<LocalDate, WDCRecord> filterByKPValue(Map<LocalDate, WDCRecord> records, Double greaterThan) {
        return records.entrySet().stream().
            parallel().collect(
                Collectors.toMap(
                        Map.Entry::getKey, v -> v.getValue().filterByKPValue(greaterThan)
                )
        );
    }

    // after filtering by kP it may appear that certain days were "quite" meaning zero kP was low all the day
    public static Map<LocalDate, WDCRecord> removeQuiteDays(Map<LocalDate, WDCRecord> records) {
        return records.entrySet().stream().parallel().filter(
                x -> x.getValue().length() > 0).collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
        );
    }

    // when filter performed, it is necessary to get per-timeframe statistics, especially for night hours (0-3 | 3-6 | 21-24)
    public static Map<LocalDate, WDCRecord> filterByTimeframe(Map<LocalDate, WDCRecord> records, List<Timeframe> tf) {
        return records.entrySet().stream().parallel().collect(
                Collectors.toMap(
                        Map.Entry::getKey, v -> v.getValue().getMultipleKPByTimeframe(tf)
                )
        );
    }

    // performs per-month filtering for more precise statistics
    public static Map<LocalDate, WDCRecord> onlyCertainMonth(Map<LocalDate, WDCRecord> records, Integer month) {
        return records.entrySet().stream().parallel().filter(
                x -> x.getKey().getMonthValue() == month
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // direct/reverse sorting procedure
    public static Map<LocalDate, WDCRecord> sortedByDate(Map<LocalDate, WDCRecord> records, Boolean reverseOrder) {
        TreeMap<LocalDate, WDCRecord> sorted;
        if (reverseOrder) {
            sorted = new TreeMap<>(Collections.reverseOrder());
            sorted.putAll(records);
        } else {
            sorted = new TreeMap<>(records);
        }

        return sorted;
    }

}
