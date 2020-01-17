package project.aurora.wdc.util;


import project.aurora.wdc.timeframe.Timeframe;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class TimeframeUtils {

    // we consider that the nightime is somewhere between 9PM to 6AM
    public static Boolean isNightTime(Timeframe t) {
        return (
                t == Timeframe.TIME03 ||
                t == Timeframe.TIME2124 ||
                t == Timeframe.TIME36
        );
    }

    protected static List<Integer> toHours(Timeframe tf) {
        var parts = tf.toString().split("TIME");
        if (parts[1].length() == 2) {
            return IntStream.rangeClosed(
                    Integer.parseInt(String.valueOf(parts[1].charAt(0))),
                    Integer.parseInt(String.valueOf(parts[1].charAt(1)))
            ).boxed().collect(Collectors.toList());
        } else {
            return IntStream.rangeClosed(
                    Integer.parseInt(parts[1].substring(0, 2)),
                    Integer.parseInt(parts[1].substring(2, 4))
            ).boxed().collect(Collectors.toList());
        }
    }

    public static Set<Integer> toHours(List<Timeframe> tfs) {
        var f = new HashSet<Integer>();
        tfs.parallelStream().forEach(tf -> f.addAll(toHours(tf)));
        return f;
    }

    public static Set<Long> toUNIXepoch(LocalDate date, ZoneId zoneID, List<Timeframe> tfs) {
        var hours = TimeframeUtils.toHours(tfs);
        var f = new HashSet<Long>();
        hours.parallelStream().forEach(x -> {
            if (x == 24) {
                f.add(date.atTime(23, 59).atZone(zoneID).toEpochSecond());
            } else {
                f.add(date.atTime(x, 0).atZone(zoneID).toEpochSecond());
            }
        });

        return f;
    }

}
