package com.example.util;

import java.io.FileInputStream;
import java.util.Properties;

import com.example.exception.FileHandlingException;

public class DBPropertyUtil {
    
    public static String getDriver(String filename) throws Exception {
        FileInputStream fis = new FileInputStream(filename);
        Properties props = new Properties();
        props.load(fis);

        return props.getProperty("driver");
    }

    public static String getConnectionString(String filename) throws Exception {
        FileInputStream fis = null;
        Properties props = null;
        try {
            fis = new FileInputStream(filename);
            props = new Properties();
            props.load(fis);
        } catch (Exception e) {
            // Handle file not found exception
            throw new FileHandlingException("File not found: " + e.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        // Fix the connection string format
        String connectionString = String.format(
            "jdbc:%s://%s:%s/%s?user=%s&password=%s",
            props.getProperty("protocol"),   // "mysql"
            props.getProperty("host"),       // "localhost"
            props.getProperty("port"),       // "3306"
            props.getProperty("database"),   // "petpals"
            props.getProperty("user"),       // "root"
            props.getProperty("password")    // "password"
        );

        return connectionString;
    }
}