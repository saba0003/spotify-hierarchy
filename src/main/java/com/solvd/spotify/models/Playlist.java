package com.solvd.spotify.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter @Setter @ToString
@XmlRootElement(name = "playlist")
@XmlAccessorType(XmlAccessType.FIELD)
public class Playlist {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    @XmlElement
    private Integer id;

    @XmlElement
    private String title;

    @XmlElementWrapper(name = "tracks")
    @XmlElement(name = "track")
    private List<Track> tracks;

    // default constructor required by JAXB
    public Playlist() {
        id = ID_GENERATOR.incrementAndGet();
    }
}
