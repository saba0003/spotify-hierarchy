package com.solvd.spotify.parsers.xml.stax;

import com.solvd.spotify.domain.models.Artist;
import com.solvd.spotify.domain.models.Band;
import com.solvd.spotify.domain.models.commons.Genre;
import com.solvd.spotify.domain.models.commons.MusicCreator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import java.lang.reflect.InvocationTargetException;

public class MusicCreatorsStaxParser<T extends MusicCreator> extends StaxParser<T> {

    private final Class<T> clazz;

    public MusicCreatorsStaxParser(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected String collectionTag() {
        if (clazz == Band.class)
            return "bands";
        else if (clazz == Artist.class)
            return "artists";
        throw new IllegalStateException("Unsupported type: " + clazz.getSimpleName() + "!");
    }

    @Override
    protected String itemTag() {
        if (clazz == Band.class)
            return "band";
        else if (clazz == Artist.class)
            return "artist";
        throw new IllegalStateException("Unsupported type: " + clazz.getSimpleName() + "!");
    }

    @Override
    protected T createItem() { // TODO: gotta clean up messy catch blocks
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void handleStartElement(String tag, XMLEventReader reader, T musicCreator) throws XMLStreamException {
        if ("name".equals(tag))
            setName(reader, musicCreator);
        else if ("genre".equals(tag))
            setGenre(reader, musicCreator);
    }

    @Override
    protected void handleEndElement(String tag, XMLEventReader reader, T musicCreator) throws XMLStreamException {
        if ("genre".equals(tag))
            setAlbums(reader, musicCreator);
    }

    private void setName(XMLEventReader reader, T musicCreator) throws XMLStreamException {
        musicCreator.setName(readText(reader));
    }

    private void setGenre(XMLEventReader reader, T musicCreator) throws XMLStreamException {
        musicCreator.setGenre(readEnum(reader, Genre.class));
    }

    private void setAlbums(XMLEventReader reader, T musicCreator) throws XMLStreamException {
        musicCreator.setAlbums(new AlbumsStaxParser().parseCollection(reader));
    }
}
