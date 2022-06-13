package com.application.service;

public interface PropertyService {
    Integer getInt(String property);

    Double getDouble(String property);

    String getString(String property);

    Boolean getBoolean(String property);

    Integer getIntOrDefault(String property, Integer defaultValue);

    Double getDoubleOrDefault(String property, Double defaultValue);

    String getStringOrDefault(String property, String defaultValue);

    Boolean getBooleanOrDefault(String property, Boolean defaultValue);
}
