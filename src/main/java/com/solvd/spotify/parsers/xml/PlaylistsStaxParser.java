package com.solvd.spotify.parsers.xml;

import com.solvd.spotify.models.Playlist;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;

public class PlaylistsStaxParser extends StaxParser<Playlist> {

    @Override
    protected String collectionTag() {
        return "playlists";
    }

    @Override
    protected String itemTag() {
        return "playlist";
    }

    @Override
    protected Playlist createItem() {
        return new Playlist();
    }

    @Override
    protected void handleStartElement(String tag, XMLEventReader reader, Playlist playlist) throws XMLStreamException {
        if ("title".equals(tag))
            setTitle(reader, playlist);
        else
            throw new IllegalStateException("Program shouldn't be here!");
    }

    @Override
    protected void handleEndElement(String tag, XMLEventReader reader, Playlist playlist) throws XMLStreamException {
        if ("title".equals(tag))
            setTracks(reader, playlist);
        // TODO: Exception handling can be added for illegal states
    }

    private void setTitle(XMLEventReader reader, Playlist playlist) throws XMLStreamException {
        playlist.setTitle(readText(reader));
    }

    private void setTracks(XMLEventReader reader, Playlist playlist) throws XMLStreamException {
        playlist.setTracks(new TracksStaxParser().parseCollection(reader));
    }
}
