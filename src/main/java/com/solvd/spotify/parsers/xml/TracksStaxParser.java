package com.solvd.spotify.parsers.xml;

import com.solvd.spotify.models.Track;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TracksStaxParser extends Parcelable<Track> {

    @Override
    protected List<Track> parserLogic(XMLEventReader reader) throws XMLStreamException {
        List<Track> tracks = null;
        Track currentTrack = null;
        XMLEvent event;

        while (reader.hasNext()) {
            event = reader.nextEvent();

            if (!event.isStartElement() && !event.isEndElement())
                continue;

            if (event.isStartElement()) {
                String tag = event.asStartElement().getName().getLocalPart();

                switch (tag) {
                    case "tracks" -> tracks = new ArrayList<>();
                    case "track" -> currentTrack = new Track();
                    case "title" -> {
                        if (currentTrack == null)
                            throw new NullPointerException(PREFIX.formatted("setTitle", "currentTrack"));
                        setTitle(reader, currentTrack);
                    }
                    case "durationSeconds" -> {
                        if (currentTrack == null)
                            throw new NullPointerException(PREFIX.formatted("setDuration", "currentTrack"));
                        setDuration(reader, currentTrack);
                    }
                    case "explicit" -> {
                        if (currentTrack == null)
                            throw new NullPointerException(PREFIX.formatted("setExplicit", "currentTrack"));
                        setExplicit(reader, currentTrack);
                    }
                    case "lastPlayedAt" -> {
                        if (currentTrack == null)
                            throw new NullPointerException(PREFIX.formatted("setLastPlayedAt", "currentTrack"));
                        setLastPlayedAt(reader, currentTrack);
                    }
                }
            } else if (event.isEndElement()) {
                String tag = event.asEndElement().getName().getLocalPart();
                if (tag.equals("track") && currentTrack != null) {
                    if (tracks == null)
                        throw new NullPointerException(PREFIX.formatted("add", "tracks"));
                    tracks.add(currentTrack);
                } else if (tag.equals("tracks")) {
                    if (tracks == null)
                        throw new NullPointerException("'tracks' variable is null!");
                    return tracks;
                }
            }
        }

        throw new IllegalStateException("Program mustn't be here!");
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
