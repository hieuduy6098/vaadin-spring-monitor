package com.az1.app.utils;

public class TimeUtils {
    /**
     * Formats the uptime duration from seconds into a human-readable format (e.g., d hh:mm:ss).
     *
     * @param seconds Uptime duration in seconds.
     * @return A string representing the formatted uptime.
     */
    public static String formatUptime(long seconds) {
        long days = seconds / (60 * 60 * 24);
        seconds %= (60 * 60 * 24);
        long hours = seconds / (60 * 60);
        seconds %= (60 * 60);
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%d d %02d:%02d:%02d", days, hours, minutes, seconds);
    }
}
