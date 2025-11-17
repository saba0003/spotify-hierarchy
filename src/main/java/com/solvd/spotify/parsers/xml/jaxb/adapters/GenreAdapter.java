package com.solvd.spotify.parsers.xml.jaxb.adapters;

import com.solvd.spotify.domain.models.commons.Genre;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class GenreAdapter extends XmlAdapter<String, Genre> {

    @Override
    public Genre unmarshal(String v) {
        return v == null ? null : Genre.valueOf(v.toUpperCase());
    }
    @Override
    public String marshal(Genre g) {
        if (g == null) return null;
        String name = g.name().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
