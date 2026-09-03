package com.contactapp.automation.utils;

public class ConfigReader {

    private static final String DEFAULT_BASE_URL = "http://localhost:3000";

    private ConfigReader() {
    }

    public static String getBaseUrl() {
        return System.getProperty("baseUrl", DEFAULT_BASE_URL);
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }
}
