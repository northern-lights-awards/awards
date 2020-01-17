package project.aurora.wdc.model;

import project.aurora.wdc.parser.WDCRecordParser;
import project.aurora.wdc.timeframe.Timeframe;
import project.aurora.wdc.util.TimeframeUtils;

import java.util.*;
import java.util.stream.Collectors;


public class WDCRecord {

    // 3-hourly Kp indices:
    // - 0-3
    // - 3-6
    // - 6-9
    // - 9-12
    // - 12-15
    // - 15-18
    // - 18-21
    // - 21-24
    // dataset format:
    // {
    //     "0-3": 3.3,
    //     "3-6": 3.7,
    //     ...
    // }
    private Map<Timeframe, Double> hourlyKP;

    private WDCRecord(Map<Timeframe, Double> kP) {
        hourlyKP = kP;
    }

    public WDCRecord(String wdcRecordAsString) {
        this.hourlyKP = WDCRecordParser.read(wdcRecordAsString);
    }

    public int length() {
        return hourlyKP.size();
    }

    public Map.Entry<Timeframe, Double> getHighestKPThroughTheDay() {
        return hourlyKP.entrySet().parallelStream().max(Comparator.comparingDouble(Map.Entry::getValue)).get();
    }

    public Map.Entry<Timeframe, Double> getHighestKPatNight() {
        return hourlyKP.entrySet().parallelStream().filter(
                x -> TimeframeUtils.isNightTime(x.getKey())
        ).max(Comparator.comparingDouble(Map.Entry::getValue)).get();
    }

    public String toString() {
        return hourlyKP.keySet().parallelStream()
                .map(key -> key + "=" + hourlyKP.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    public WDCRecord getMultipleKPByTimeframe(List<Timeframe> tfs) {
        var f = new HashMap<Timeframe, Double>();
        tfs.parallelStream().forEach(tf -> f.put(tf, hourlyKP.get(tf)));

        return new WDCRecord(f);
    }

    public WDCRecord filterByKPValue(Double greaterThan) {
        return new WDCRecord(hourlyKP.entrySet().parallelStream().filter(
                x -> x.getValue() >= greaterThan
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public ArrayList<Timeframe> getTimeframes() {
        return new ArrayList<>(hourlyKP.keySet());
    }
    
}
