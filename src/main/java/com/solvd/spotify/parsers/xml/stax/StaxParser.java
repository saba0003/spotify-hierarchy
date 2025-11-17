package com.solvd.spotify.parsers.xml.stax;

import com.solvd.spotify.parsers.xml.exceptions.XmlParsingException;
import com.solvd.spotify.utils.XMLValidator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

abstract class StaxParser<T> {

    private static final String ERR = "'%s()' method couldn't be called upon '%s' variable when the latter is null!";

    public List<T> parse(String xmlPath, String schemaPath) {
        XMLValidator.validateAgainstSchema(xmlPath, schemaPath);
        try (FileInputStream fis = new FileInputStream(xmlPath)) {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(fis);
            return parseCollection(reader);
        } catch (XMLStreamException | IOException e) {
            throw new XmlParsingException(e.getMessage(), e);
        }
    }

    protected List<T> parseCollection(XMLEventReader reader) throws XMLStreamException {
        List<T> collection = null;
        T current = null;
        XMLEvent event;

        while (reader.hasNext()) {
            event = reader.nextEvent();

            if (!event.isStartElement() && !event.isEndElement())
                continue;

            if (event.isStartElement()) {
                String tag = event.asStartElement().getName().getLocalPart();
                if (tag.equals(collectionTag())) {
                    collection = new ArrayList<>();
                } else if (tag.equals(itemTag())) {
                    current = createItem();
                } else {
                    if (current == null)
                        throw new NullPointerException(ERR.formatted("handleStartElement", "current"));
                    handleStartElement(tag, reader, current);
                }
            } else if (event.isEndElement()) {
                String tag = event.asEndElement().getName().getLocalPart();
                if (tag.equals(itemTag())) {
                    if (collection == null)
                        throw new NullPointerException(ERR.formatted("add", "collection"));
                    collection.add(current);
                } else if (tag.equals(collectionTag())) {
                    if (collection == null)
                        throw new NullPointerException("'collection' variable is null!");
                    return collection;
                } else {
                    // TODO: remember that most elements can be unordered. right now, changing the order in the xml data files could potentially cause errors
                    if (current == null)
                        throw new NullPointerException(ERR.formatted("handleEndElement", "current"));
                    handleEndElement(tag, reader, current);
                }
            }
        }

        throw new IllegalStateException("Unexpected end of XML stream");
    }

    /** Hooks */
    protected abstract String collectionTag();
    protected abstract String itemTag();
    protected abstract T createItem();
    protected abstract void handleStartElement(String tag, XMLEventReader reader, T current) throws XMLStreamException;
    protected abstract void handleEndElement(String tag, XMLEventReader reader, T current) throws XMLStreamException;

    protected String readText(XMLEventReader reader) throws XMLStreamException {
        XMLEvent event = reader.nextEvent();
        if (!event.isCharacters())
            throw new XMLStreamException("Expected character data, found: " + event);
        return event.asCharacters().getData().trim();
    }

    protected <E extends Enum<E>> E readEnum(XMLEventReader reader, Class<E> enumClass) throws XMLStreamException {
        String text = readText(reader);
        try {
            return Enum.valueOf(enumClass, text.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new XMLStreamException("Invalid value '" + text + "' for enum " + enumClass.getSimpleName(), e);
        }
    }
}
