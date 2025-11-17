package com.solvd.spotify;

import com.solvd.spotify.domain.models.Artist;
import com.solvd.spotify.parsers.xml.stax.MusicCreatorsStaxParser;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        MusicCreatorsStaxParser<Artist> artistsParser = new MusicCreatorsStaxParser<>(Artist.class);
        List<Artist> artists = artistsParser.parse("src/main/resources/raw/xml/artists.xml", "src/main/resources/schemas/xsd/artists-schema.xsd");
        artists.forEach(artist -> System.out.println(artist));
    }
}
