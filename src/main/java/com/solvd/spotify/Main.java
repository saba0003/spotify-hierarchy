package com.solvd.spotify;

import com.solvd.spotify.models.Band;
import com.solvd.spotify.parsers.xml.MusicCreatorsStaxParser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) {

        MusicCreatorsStaxParser<Band> bandsParser = new MusicCreatorsStaxParser<>(Band.class);
        List<Band> bands = bandsParser.parse("src/main/resources/raw/xml/bands.xml", "src/main/resources/schemas/bands-schema.xsd");
        bands.forEach(band -> log.info(band.toString()));
    }
}
