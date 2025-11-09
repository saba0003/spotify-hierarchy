package com.solvd.spotify;

import com.solvd.spotify.models.Band;
import com.solvd.spotify.parsers.MusicCreatorStaxParser;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) {

        MusicCreatorStaxParser<Band> parser = new MusicCreatorStaxParser<>(Band.class);

        try (FileInputStream fis = new FileInputStream("src/main/resources/raw/xml/bands.xml")) {
            List<Band> bands = parser.parse(fis, "src/main/resources/schemas/bands-schema.xsd");
            for (Band b : bands) {
                log.info("Band: {}", b.getName());
                if (b.getAlbums() != null)
                    for (var album : b.getAlbums())
                        log.info("Album: {} ({})", album.getTitle(), album.getReleaseDate());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

//File xmlFile = new File("src/main/resources/raw/xml/spotify-catalogue.xml");
//
//// 1. Setup DocumentBuilderFactory with XInclude support
//DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        dbf.setNamespaceAware(true);
//        dbf.setXIncludeAware(true); // This enables processing of <xi:include>
//
//DocumentBuilder db = dbf.newDocumentBuilder();
//
//// 2. Parse XML and expand XIncludes
//Document doc = db.parse(xmlFile);
//
//// 3. Convert Document back to InputStream for your parser
//DOMSource source = new DOMSource(doc);
//// You can use a Transformer to write DOMSource to a ByteArrayOutputStream
//ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(baos));
//InputStream preprocessedInput = new ByteArrayInputStream(baos.toByteArray());
//
//// 4. Pass preprocessed input to your parser
//MusicCreatorStaxParser<Band> parser = new MusicCreatorStaxParser<>(Band.class);
//List<Band> bands = parser.parse(preprocessedInput, "src/main/resources/schemas/spotify-catalogue-schema.xsd");
