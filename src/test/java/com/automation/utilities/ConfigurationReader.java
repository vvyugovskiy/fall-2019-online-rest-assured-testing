package com.automation.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    private static Properties properties;

    static {
        try {
            try (FileInputStream fileInputStream = new FileInputStream("configuration.properties")) {
                properties = new Properties();
                properties.load(fileInputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Faile to load properties file!");
        }
    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
