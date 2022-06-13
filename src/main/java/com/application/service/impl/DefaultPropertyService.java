package com.application.service.impl;

import com.application.service.PropertyService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class DefaultPropertyService implements PropertyService {
    private static Properties PROPERTIES = null;
    private static final String PROPERTY_FILE = Objects.requireNonNull(DefaultPropertyService.class.getClassLoader().getResource("project.properties")).getPath();;

    private String getCurrentProperty(String property) throws IllegalArgumentException
    {
        if (property == null)
        {
            throw new IllegalArgumentException("Property should not be null");
        }
        if (PROPERTIES == null)
        {
            PROPERTIES = new Properties();
        }

        try
        {
            FileInputStream fis = new FileInputStream(PROPERTY_FILE);
            PROPERTIES.load(fis);
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException("File can't be found: " + PROPERTY_FILE, e);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("Error loading properties", e);
        }

        return PROPERTIES.getProperty(property);
    }

    @Override
    public Integer getInt(String property)
    {
        return Integer.valueOf(getCurrentProperty(property));
    }

    @Override
    public Double getDouble(String property)
    {
        return Double.valueOf(getCurrentProperty(property));
    }

    @Override
    public String getString(String property)
    {
        return getCurrentProperty(property);
    }

    @Override
    public Boolean getBoolean(String property)
    {
        return Boolean.valueOf(getCurrentProperty(property));
    }

    @Override
    public Integer getIntOrDefault(String property, Integer defaultValue)
    {
        try
        {
            String currentProperty = getCurrentProperty(property);
            return Integer.valueOf(currentProperty);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    @Override
    public Double getDoubleOrDefault(String property, Double defaultValue)
    {
        try
        {
            String currentProperty = getCurrentProperty(property);
            return Double.valueOf(currentProperty);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    @Override
    public String getStringOrDefault(String property, String defaultValue)
    {
        try
        {
            String currentProperty = getCurrentProperty(property);
            return String.valueOf(currentProperty);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }

    @Override
    public Boolean getBooleanOrDefault(String property, Boolean defaultValue)
    {
        try
        {
            String currentProperty = getCurrentProperty(property);
            return Boolean.valueOf(currentProperty);
        }
        catch (Exception e)
        {
            return defaultValue;
        }
    }
}
