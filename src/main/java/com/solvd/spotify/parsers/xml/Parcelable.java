package com.solvd.spotify.parsers.xml;

import com.solvd.spotify.exceptions.XmlParsingException;
import com.solvd.spotify.utils.XMLValidator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

abstract class Parcelable<E> {

    protected static final String PREFIX = "'%s()' method couldn't be called upon '%s' variable when the latter is null!";

    public List<E> parse(String xmlPath, String schemaPath) {
        XMLValidator.validateAgainstSchema(xmlPath, schemaPath);
        try (FileInputStream fis = new FileInputStream(xmlPath)) {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(fis);
            return parserLogic(reader);
        } catch (XMLStreamException | IOException e) {
            throw new XmlParsingException(e.getMessage(), e);
        }
    }

    protected abstract List<E> parserLogic(XMLEventReader reader) throws XMLStreamException;

    protected String readText(XMLEventReader reader) throws XMLStreamException {
        return reader.nextEvent().asCharacters().getData();
    }
}
