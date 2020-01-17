package project.aurora.utils;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class LocalDateUtils {

    public static LinkedHashMap<Month, List<LocalDate>> divideByMonth(Set<LocalDate> dates) {
        var ret = new LinkedHashMap<Month, List<LocalDate>>();
        if (dates.size() > 0) {
            var f = new HashMap<Month, List<LocalDate>>();
            dates.forEach(date -> {
                var month = date.getMonth();
                var ds = f.getOrDefault(month, new ArrayList<>());
                ds.add(date);
                f.put(month, ds);
            });
            f.entrySet().parallelStream()
                .sorted(Comparator.comparingInt(lst -> lst.getValue().size()))
                .forEachOrdered(x -> ret.put(x.getKey(), x.getValue())
            );
        }
        return ret;
    }

    public static String toString(LinkedHashMap<Month, List<LocalDate>> dates, Boolean detailed) {
        return dates.keySet().stream()
                .map(key -> {
                    var s = detailed ? dates.get(key).toString() : dates.get(key).size();
                    return "\t" + key + " : <" + s + " days>";
                })
                .collect(Collectors.joining(", \n", "{\n", "\n}"));
    }

}
