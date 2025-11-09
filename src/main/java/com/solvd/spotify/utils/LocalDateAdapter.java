package com.solvd.spotify.utils;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;

import static com.solvd.spotify.config.GlobalProperties.DATE_FORMATTER;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String valueType) {
        return (valueType == null || valueType.isBlank()) ? null : LocalDate.parse(valueType, DATE_FORMATTER);
    }

    @Override
    public String marshal(LocalDate boundType) {
        return (boundType == null) ? null : boundType.format(DATE_FORMATTER);
    }
}
