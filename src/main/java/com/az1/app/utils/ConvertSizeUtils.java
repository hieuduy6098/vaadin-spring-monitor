package com.az1.app.utils;

public class ConvertSizeUtils {
    /**
     * The formatSize method converts a size in bytes into a more readable format (GB, MB, KB, B).
     */
    public static String formatSize(long bytes) {
        if (bytes >= 1024 * 1024 * 1024) {
            double gigabytes = Math.round((double) bytes / (1024 * 1024 * 1024) * 100) / 100.0;
            return gigabytes + " GB";
        } else if (bytes >= 1024 * 1024) {
            double megabytes = Math.round((double) bytes / (1024 * 1024) * 100) / 100.0;
            return megabytes + " MB";
        } else if (bytes >= 1024) {
            double kilobytes = Math.round((double) bytes / 1024.0 * 100) / 100.0;
            return kilobytes + " KB";
        } else {
            return bytes + " B";
        }
    }
}
