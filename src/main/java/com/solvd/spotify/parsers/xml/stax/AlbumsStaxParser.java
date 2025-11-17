package com.solvd.spotify.parsers.xml.stax;

import com.solvd.spotify.domain.models.AlbumXml;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import java.time.LocalDate;

public class AlbumsStaxParser extends StaxParser<AlbumXml> {

    @Override
    protected String collectionTag() {
        return "albums";
    }

    @Override
    protected String itemTag() {
        return "album";
    }

    @Override
    protected AlbumXml createItem() {
        return new AlbumXml();
    }

    @Override
    protected void handleStartElement(String tag, XMLEventReader reader, AlbumXml albumXml) throws XMLStreamException {
        if ("title".equals(tag))
            setTitle(reader, albumXml);
        else if ("releaseDate".equals(tag))
            setReleaseDate(reader, albumXml);
        else
            throw new IllegalStateException("Program shouldn't be here!");
    }

    @Override
    protected void handleEndElement(String tag, XMLEventReader reader, AlbumXml albumXml) throws XMLStreamException {
        if ("releaseDate".equals(tag))
            setTracks(reader, albumXml);
        // TODO: Exception handling can be added for illegal states
    }

    private void setTitle(XMLEventReader reader, AlbumXml albumXml) throws XMLStreamException {
        albumXml.setTitle(readText(reader));
    }

    private void setReleaseDate(XMLEventReader reader, AlbumXml albumXml) throws XMLStreamException {
        albumXml.setReleaseDate(LocalDate.parse(readText(reader)));
    }

    private void setTracks(XMLEventReader reader, AlbumXml albumXml) throws XMLStreamException {
        albumXml.setTracks(new TracksStaxParser().parseCollection(reader));
    }
}
