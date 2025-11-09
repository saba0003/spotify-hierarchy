package com.solvd.spotify.config;

import com.solvd.spotify.exceptions.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public final class GlobalProperties {

    private static final Logger log = LogManager.getLogger(GlobalProperties.class);
    private static final Properties PROPS = new Properties();

    public static DateTimeFormatter DATE_FORMATTER;
    public static DateTimeFormatter DATE_TIME_FORMATTER;

    static {
        try {
            load();
        } catch (ConfigurationException e) {
            log.error("Failed to load global properties: {}", e.getMessage());
        }
    }

    private GlobalProperties() {
        throw new IllegalStateException("Utility class, do not instantiate!");
    }

    private static void load() throws ConfigurationException {
        try (InputStream input = GlobalProperties.class.getClassLoader().getResourceAsStream("global.properties")) {

            if (input == null)
                throw new ConfigurationException("global.properties file not found in resources");

            PROPS.load(input);

            String datePattern = PROPS.getProperty("date.format");
            String datetimePattern = PROPS.getProperty("datetime.format");

            if (datePattern == null || datetimePattern == null)
                throw new ConfigurationException("Missing required date format properties");

            DATE_FORMATTER = DateTimeFormatter.ofPattern(datePattern);
            DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(datetimePattern);

        } catch (IOException e) {
            throw new ConfigurationException("Failed to read global.properties", e);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("Invalid date format pattern in global.properties", e);
        }
    }
}
