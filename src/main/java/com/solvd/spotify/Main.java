package com.solvd.spotify;

import com.solvd.spotify.models.Playlist;
import com.solvd.spotify.parsers.xml.PlaylistsStaxParser;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) {

        PlaylistsStaxParser playlistsParser = new PlaylistsStaxParser();
        List<Playlist> playlists = playlistsParser.parse("src/main/resources/raw/xml/playlists.xml", "src/main/resources/schemas/playlists-schema.xsd");
        playlists.forEach(playlist -> log.info(playlist.toString()));
    }
}
