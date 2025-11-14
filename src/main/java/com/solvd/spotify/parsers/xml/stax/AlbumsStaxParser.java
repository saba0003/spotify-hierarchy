package com.solvd.spotify.parsers.xml.stax;

import com.solvd.spotify.models.Album;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import java.time.LocalDate;

public class AlbumsStaxParser extends StaxParser<Album> {

    @Override
    protected String collectionTag() {
        return "albums";
    }

    @Override
    protected String itemTag() {
        return "album";
    }

    @Override
    protected Album createItem() {
        return new Album();
    }

    @Override
    protected void handleStartElement(String tag, XMLEventReader reader, Album album) throws XMLStreamException {
        if ("title".equals(tag))
            setTitle(reader, album);
        else if ("releaseDate".equals(tag))
            setReleaseDate(reader, album);
        else
            throw new IllegalStateException("Program shouldn't be here!");
    }

    @Override
    protected void handleEndElement(String tag, XMLEventReader reader, Album album) throws XMLStreamException {
        if ("releaseDate".equals(tag))
            setTracks(reader, album);
        // TODO: Exception handling can be added for illegal states
    }

    private void setTitle(XMLEventReader reader, Album album) throws XMLStreamException {
        album.setTitle(readText(reader));
    }

    private void setReleaseDate(XMLEventReader reader, Album album) throws XMLStreamException {
        album.setReleaseDate(LocalDate.parse(readText(reader)));
    }

    private void setTracks(XMLEventReader reader, Album album) throws XMLStreamException {
        album.setTracks(new TracksStaxParser().parseCollection(reader));
    }
}
