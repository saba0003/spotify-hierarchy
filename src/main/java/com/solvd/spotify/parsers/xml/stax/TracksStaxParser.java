package com.solvd.spotify.parsers.xml.stax;

import com.solvd.spotify.models.Track;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import java.time.LocalDateTime;

public class TracksStaxParser extends StaxParser<Track> {

    @Override
    protected String collectionTag() {
        return "tracks";
    }

    @Override
    protected String itemTag() {
        return "track";
    }

    @Override
    protected Track createItem() {
        return new Track();
    }

    @Override
    protected void handleStartElement(String tag, XMLEventReader reader, Track track) throws XMLStreamException {
        switch (tag) {
            case "title" -> setTitle(reader, track);
            case "durationSeconds" -> setDuration(reader, track);
            case "explicit" -> setExplicit(reader, track);
            case "lastPlayedAt" -> setLastPlayedAt(reader, track);
            default -> throw new IllegalStateException("Program shouldn't be here!");
        }
    }

    @Override
    protected void handleEndElement(String tag, XMLEventReader reader, Track track) {
        // Do nothing
    }

    private void setTitle(XMLEventReader reader, Track track) throws XMLStreamException {
        track.setTitle(readText(reader));
    }

    private void setDuration(XMLEventReader reader, Track track) throws XMLStreamException {
        track.setDurationSeconds(Integer.parseInt(readText(reader)));
    }

    private void setExplicit(XMLEventReader reader, Track track) throws XMLStreamException {
        track.setExplicit(Boolean.parseBoolean(readText(reader)));
    }

    private void setLastPlayedAt(XMLEventReader reader, Track track) throws XMLStreamException {
        track.setLastPlayedAt(LocalDateTime.parse(readText(reader)));
    }
}
