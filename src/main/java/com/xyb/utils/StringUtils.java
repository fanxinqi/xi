package com.xyb.utils;

public class StringUtils {
    public static boolean isBlank(String s) {
        if (s == null || s.length() <= 0) {
            return true;
        }
        return false;

    }
}
