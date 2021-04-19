package com.thoughtworks.apiAssignment.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class PropertyUtils {
	
	public static String getPropertyValue (String key) {
		Properties properties = new Properties();
		try (InputStream is = PropertyUtils.class.getResourceAsStream("/test.properties")) {
            properties.load(is);
        } catch (IOException e) {
            throw new PropertyUtilsException("Failed to read properties file. ERROR: {}", e);
        }
        return properties.getProperty(key);
    }

    @Slf4j //simple logger for java
    private static final class PropertyUtilsException extends RuntimeException {
        public PropertyUtilsException(String message, Throwable throwable) {
            super(message, throwable);
//            log.error("PropertyUtilsException: {}, Stacktrace: {}", message, throwable);
        }
    }
	
}
