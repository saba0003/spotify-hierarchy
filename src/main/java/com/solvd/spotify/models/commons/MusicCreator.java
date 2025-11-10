package com.solvd.spotify.models.commons;

import com.solvd.spotify.models.Album;
import com.solvd.spotify.models.Artist;
import com.solvd.spotify.models.Band;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter @Setter @ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Artist.class, Band.class})
public abstract class MusicCreator {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    @XmlElement
    protected Integer id;

    @XmlElement
    protected String name;

    @XmlElement
    protected Genre genre;

    @XmlElementWrapper(name = "albums")
    @XmlElement(name = "album")
    protected List<Album> albums;

    // default constructor required by JAXB
    protected MusicCreator() {
        id = ID_GENERATOR.incrementAndGet();
    }
}
