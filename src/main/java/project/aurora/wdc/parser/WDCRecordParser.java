package project.aurora.wdc.parser;

import project.aurora.wdc.timeframe.Timeframe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


public class WDCRecordParser {

    public static Map<Timeframe, Double> read(String wdcRecordAsString) {
        var hourlyKP = new HashMap<Timeframe, Double>();
        final var counter = new AtomicInteger(0);

        var parts = wdcRecordAsString
                .substring(12, 28)
                .split("(?<=\\G.{2})");

        IntStream.range(0, parts.length).forEach(
                idx -> hourlyKP.put(
                        Timeframe.valueOf("TIME" + idx * 3 + "" + (idx + 1) * 3),
                        Double.parseDouble(parts[idx]) / 10
                )
        );

        return hourlyKP;
    }

    public static LocalDate toDate(String wdcRecordAsString) {
        return toDate(
                // yy
                wdcRecordAsString.substring(0, 2),
                // mm
                wdcRecordAsString.substring(2, 4),
                // dd
                wdcRecordAsString.substring(4, 6)
        );
    }

    public static LocalDate toDate(String yy, String mm, String dd) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(
                    String.format("20%s-%s-%s", yy, mm, dd)
            ).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            return null;
        }
    }

}
