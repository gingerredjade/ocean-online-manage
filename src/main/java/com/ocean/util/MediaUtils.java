package com.ocean.util;

import org.apache.commons.lang.StringUtils;

public class MediaUtils {

    public static boolean isVideo(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return false;
        }

        return fileName.toLowerCase().endsWith(".mp4") ||
                fileName.toLowerCase().endsWith(".avi") ||
                fileName.toLowerCase().endsWith(".wmv") ||
                fileName.toLowerCase().endsWith(".flv");
    }
}
