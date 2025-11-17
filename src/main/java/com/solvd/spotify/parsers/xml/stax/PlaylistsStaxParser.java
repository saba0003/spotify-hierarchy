package com.solvd.spotify.parsers.xml.stax;

import com.solvd.spotify.domain.models.PlaylistXml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;

public class PlaylistsStaxParser extends StaxParser<PlaylistXml> {

    @Override
    protected String collectionTag() {
        return "playlists";
    }

    @Override
    protected String itemTag() {
        return "playlist";
    }

    @Override
    protected PlaylistXml createItem() {
        return new PlaylistXml();
    }

    @Override
    protected void handleStartElement(String tag, XMLEventReader reader, PlaylistXml playlistXml) throws XMLStreamException {
        if ("title".equals(tag))
            setTitle(reader, playlistXml);
        else
            throw new IllegalStateException("Program shouldn't be here!");
    }

    @Override
    protected void handleEndElement(String tag, XMLEventReader reader, PlaylistXml playlistXml) throws XMLStreamException {
        if ("title".equals(tag))
            setTracks(reader, playlistXml);
        // TODO: Exception handling can be added for illegal states
    }

    private void setTitle(XMLEventReader reader, PlaylistXml playlistXml) throws XMLStreamException {
        playlistXml.setTitle(readText(reader));
    }

    private void setTracks(XMLEventReader reader, PlaylistXml playlistXml) throws XMLStreamException {
        playlistXml.setTracks(new TracksStaxParser().parseCollection(reader));
    }
}
