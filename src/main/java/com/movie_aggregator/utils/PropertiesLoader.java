package com.movie_aggregator.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Eric Knapp
 *
 */
public interface PropertiesLoader {

    default Properties loadProperties(String propertiesFilePath)  {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(propertiesFilePath));
        } catch (Exception ioException) {
            ioException.printStackTrace();
            try {
                throw ioException;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }
}
