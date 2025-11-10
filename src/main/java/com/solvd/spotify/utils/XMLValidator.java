package com.solvd.spotify.utils;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class XMLValidator {

    private XMLValidator() {
        throw new IllegalStateException("Utility class - do not instantiate!");
    }

    public static void validateAgainstSchema(String xmlPath, String schemaPath) {
        try (InputStream xmlInput = new FileInputStream(xmlPath)) {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(schemaPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlInput));
        } catch (SAXException e) {
            throw new RuntimeException("XML validation failed: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("I/O error during XML validation", e);
        }
    }
}
