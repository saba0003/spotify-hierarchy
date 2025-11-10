package com.solvd.spotify.parsers.xml;

import com.solvd.spotify.models.Album;
import com.solvd.spotify.models.Track;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlbumsStaxParser extends Parcelable<Album> {

    @Override
    protected List<Album> parserLogic(XMLEventReader reader) throws XMLStreamException {
        List<Album> albums = null;
        Album currentAlbum = null;
        XMLEvent event;

        while (reader.hasNext()) {
            event = reader.nextEvent();

            if (!event.isStartElement() && !event.isEndElement())
                continue;

            if (event.isStartElement()) {
                String tag = event.asStartElement().getName().getLocalPart();

                switch (tag) {
                    case "albums" -> albums = new ArrayList<>();
                    case "album" -> currentAlbum = new Album();
                    case "title" -> {
                        if (currentAlbum == null)
                            throw new NullPointerException(PREFIX.formatted("setTitle", "currentAlbum"));
                        setTitle(reader, currentAlbum);
                    }
                    case "releaseDate" -> {
                        if (currentAlbum == null)
                            throw new NullPointerException(PREFIX.formatted("setReleaseDate", "currentAlbum"));
                        setReleaseDate(reader, currentAlbum);
                    }
                }
            } else if (event.isEndElement()) {
                String tag = event.asEndElement().getName().getLocalPart();
                if (currentAlbum == null)
                    throw new NullPointerException(PREFIX.formatted("add", "currentAlbum"));
                switch (tag) {
                    case "album" -> {
                        if (albums == null)
                            throw new NullPointerException(PREFIX.formatted("add", "albums"));
                        albums.add(currentAlbum);
                    }
                    case "releaseDate" -> setTracks(reader, currentAlbum);
                    case "albums" -> {
                        if (albums == null)
                            throw new NullPointerException("'albums' variable is null!");
                        return albums;
                    }
                }
            }
        }

        throw new IllegalStateException("Program mustn't be here!");
    }

    private void setTitle(XMLEventReader reader, Album album) throws XMLStreamException {
        album.setTitle(readText(reader));
    }

    private void setReleaseDate(XMLEventReader reader, Album album) throws XMLStreamException {
        album.setReleaseDate(LocalDate.parse(readText(reader)));
    }

    private void setTracks(XMLEventReader reader, Album album) throws XMLStreamException {
        List<Track> tracks = new TracksStaxParser().parserLogic(reader);
        album.setTracks(tracks);
    }
}
