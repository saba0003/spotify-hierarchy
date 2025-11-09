package com.solvd.spotify.parsers;

import com.solvd.spotify.exceptions.XmlParsingException;
import com.solvd.spotify.exceptions.XsdValidationException;
import com.solvd.spotify.models.Album;
import com.solvd.spotify.models.Track;
import com.solvd.spotify.models.commons.Genre;
import com.solvd.spotify.models.commons.MusicCreator;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.solvd.spotify.config.GlobalProperties.*;

public class MusicCreatorStaxParser<T extends MusicCreator> {

    private final Class<T> clazz;

    public MusicCreatorStaxParser(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> parse(InputStream input, String schemaPath) throws XmlParsingException {
        return parseLogic(input, schemaPath);
    }

    public List<T> parseLogic(InputStream input, String schemaPath) throws XmlParsingException {

        byte[] xmlBytes;
        try {
            xmlBytes = input.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ByteArrayInputStream validationStream = new ByteArrayInputStream(xmlBytes);
        validateAgainstSchema(validationStream, schemaPath);

        ByteArrayInputStream parsingStream = new ByteArrayInputStream(xmlBytes);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader;
        try {
            reader = factory.createXMLStreamReader(parsingStream);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }


        T currentCreator = null;
        Album currentAlbum = null;
        Track currentTrack = null;
        String currentContent = null;
        String currentParent = null;
        List<T> creators = new ArrayList<>();
        List<Album> albums = new ArrayList<>();
        List<Track> tracks = new ArrayList<>();

//        String rootTag;
//
//        if (currentCreator instanceof Artist) {
//            rootTag = "artist";
//        } else {
//            rootTag = "band";
//        }

        try {
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    String root = reader.getLocalName();

                    switch (root) {
                        case "bands" -> {
                            creators = new ArrayList<>();
                        }
                        case "band" -> {
                            currentCreator = clazz.getDeclaredConstructor().newInstance();
                        }
                        case "albums" -> {
                            albums = new ArrayList<>();
                        }
                        case "album" -> {
                            currentAlbum = new Album();
                            currentParent = "album";
                        }
                        case "tracks" -> {
                            tracks = new ArrayList<>();
                        }
                        case "track" -> {
                            currentTrack = new Track();
                            currentParent = "track";
                        }
                    }

                } else if (event == XMLStreamConstants.CHARACTERS) {
                    String content = reader.getText().trim();
                    if (!content.isBlank())
                        currentContent = content;
                } else if (event == XMLStreamConstants.END_ELEMENT) {
                    String root = reader.getLocalName();

                    switch (root) {
                        case "name" -> {
                            currentCreator.setName(currentContent);
                        }
                        case "genre" -> {
                            currentCreator.setGenre(Genre.valueOf(currentContent.toUpperCase()));
                        }
                        case "title" -> {
                            if (currentParent.equals("album"))
                                currentAlbum.setTitle(currentContent);
                            else
                                currentTrack.setTitle(currentContent);
                        }
                        case "releaseDate" -> {
                            currentAlbum.setReleaseDate(LocalDate.parse(currentContent));
                        }
                        case "durationSeconds" -> {
                            currentTrack.setDurationSeconds(Integer.parseInt(currentContent));
                        }
                        case "explicit" -> {
                            currentTrack.setExplicit(Boolean.parseBoolean(currentContent));
                        }
                        case "lastPlayedAt" -> {
                            currentTrack.setLastPlayedAt(LocalDateTime.parse(currentContent));
                        }
                        case "track" -> {
                            tracks.add(currentTrack);
                        }
                        case "tracks" -> {
                            currentAlbum.setTracks(tracks);
                        }
                        case "album" -> {
                            albums.add(currentAlbum);
                        }
                        case "albums" -> {
                            currentCreator.setAlbums(albums);
                        }
                        case "band" -> {
                            creators.add(currentCreator);
                        }
                        case "bands" -> {
                            return creators;
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new XmlParsingException("Exception occurred when parsing");
        } catch (InvocationTargetException e) {
            throw new XmlParsingException("asd", e);
        } catch (InstantiationException e) {
            throw new XmlParsingException("asd", e);
        } catch (IllegalAccessException e) {
            throw new XmlParsingException("asdasd", e);
        } catch (NoSuchMethodException e) {
            throw new XmlParsingException("asda", e);
        }

        return List.of();
    }

    private void validateAgainstSchema(InputStream xmlInput, String schemaPath) throws XsdValidationException {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(schemaPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlInput));
        } catch (SAXException | IOException e) {
            throw new XsdValidationException("XML validation failed: " + e.getMessage());
        }
    }
}
