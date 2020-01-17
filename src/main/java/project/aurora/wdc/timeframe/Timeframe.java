package project.aurora.wdc.timeframe;

import java.util.Arrays;
import java.util.List;


public enum Timeframe {
    // timeframes
    TIME03 {
        public String action() {
            return new String("0-3");
        }
    },
    TIME36 {
        public String action() {
            return new String("3-6");
        }
    },
    TIME69 {
        public String action() {
            return new String("6-9");
        }
    },
    TIME912 {
        public String action() {
            return new String("9-12");
        }
    },
    TIME1215 {
        public String action() {
            return new String("12-15");
        }
    },
    TIME1518 {
        public String action() {
            return new String("15-18");
        }
    },
    TIME1821 {
        public String action() {
            return new String("18-21");
        }
    },
    TIME2124 {
        public String action() {
            return new String("21-24");
        }
    }
}
