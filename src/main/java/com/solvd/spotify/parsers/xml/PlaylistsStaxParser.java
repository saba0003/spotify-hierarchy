package com.solvd.spotify.parsers.xml;

import com.solvd.spotify.models.Playlist;
import com.solvd.spotify.models.Track;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

public class PlaylistsStaxParser extends Parcelable<Playlist> {

    @Override
    protected List<Playlist> parserLogic(XMLEventReader reader) throws XMLStreamException {
        List<Playlist> playlists = null;
        Playlist currentPlaylist = null;
        XMLEvent event;

        while (reader.hasNext()) {
            event = reader.nextEvent();

            if (!event.isStartElement() && !event.isEndElement())
                continue;

            if (event.isStartElement()) {
                String tag = event.asStartElement().getName().getLocalPart();

                switch (tag) {
                    case "playlists" -> playlists = new ArrayList<>();
                    case "playlist" -> currentPlaylist = new Playlist();
                    case "title" -> {
                        if (currentPlaylist == null)
                            throw new NullPointerException(PREFIX.formatted("setTitle", "currentPlaylist"));
                        setTitle(reader, currentPlaylist);
                    }
                }
            } else if (event.isEndElement()) {
                String tag = event.asEndElement().getName().getLocalPart();
                if (currentPlaylist == null)
                    throw new NullPointerException(PREFIX.formatted("add", "currentPlaylist"));
                switch (tag) {
                    case "playlist" -> {
                        if (playlists == null)
                            throw new NullPointerException(PREFIX.formatted("add", "playlists"));
                        playlists.add(currentPlaylist);
                    }
                    case "title" -> setTracks(reader, currentPlaylist);
                    case "playlists" -> {
                        if (playlists == null)
                            throw new NullPointerException("'playlists' variable is null!");
                        return playlists;
                    }
                }
            }
        }

        throw new IllegalStateException("Program mustn't be here!");
    }

    private void setTitle(XMLEventReader reader, Playlist playlist) throws XMLStreamException {
        playlist.setTitle(readText(reader));
    }

    private void setTracks(XMLEventReader reader, Playlist playlist) throws XMLStreamException {
        List<Track> tracks = new TracksStaxParser().parserLogic(reader);
        playlist.setTracks(tracks);
    }
}
