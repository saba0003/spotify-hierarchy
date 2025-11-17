package com.solvd.spotify.parsers.xml.jaxb;

import com.solvd.spotify.domain.models.SpotifyCatalogue;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class SpotifyCatalogueLoader {

    public static SpotifyCatalogue load(String xmlPath, String schemaPath) {
        try {
            // 1. Parse XML with XInclude enabled
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            dbf.setXIncludeAware(true); // Enable XInclude
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(xmlPath));

            // 2 Remove xml:base attributes
            NodeList nodes = doc.getElementsByTagName("*");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element elem = (Element) nodes.item(i);
                elem.removeAttributeNS(XMLConstants.XML_NS_URI, "base");
            }

            // 3 Validate preprocessed document against XSD
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(schemaPath));
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(doc)); // Throws SAXException if invalid

            // 4. Create JAXB context and unmarshaller
            JAXBContext context = JAXBContext.newInstance(SpotifyCatalogue.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // 5. Unmarshal DOM document
            return (SpotifyCatalogue) unmarshaller.unmarshal(doc);

        } catch (ParserConfigurationException | JAXBException | org.xml.sax.SAXException | java.io.IOException e) {
            throw new RuntimeException("Failed to load SpotifyCatalogue", e);
        }
    }
}
