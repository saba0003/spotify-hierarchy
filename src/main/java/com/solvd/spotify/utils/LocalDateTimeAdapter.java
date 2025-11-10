package com.solvd.spotify.utils;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;

import static com.solvd.spotify.config.GlobalProperties.DATE_TIME_FORMATTER;

public final class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public LocalDateTime unmarshal(String valueType) {
        return (valueType == null || valueType.isBlank()) ? null : LocalDateTime.parse(valueType, DATE_TIME_FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime boundType) {
        return (boundType == null) ? null : boundType.format(DATE_TIME_FORMATTER);
    }
}
