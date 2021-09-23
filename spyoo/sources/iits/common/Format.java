package iits.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {
    public static String formatDate(long time) {
        return new SimpleDateFormat(Constants.YYYY_MM_DD).format(new Date(time)).toString();
    }

    public static String formatTime(long time) {
        return new SimpleDateFormat(Constants.HH_MM_SS).format(new Date(time)).toString();
    }

    public static String formatDuration(int duration) {
        int h = 0;
        int m = 0;
        int s = duration;
        if (s >= 60) {
            m = s / 60;
            s %= 60;
            if (m >= 60) {
                h = m / 60;
                m %= 60;
            }
        }
        return String.valueOf(String.valueOf(h)) + ":" + String.valueOf(m) + ":" + String.valueOf(s);
    }
}
