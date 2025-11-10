package com.solvd.spotify.models;

import com.solvd.spotify.models.commons.MusicCreator;
import com.solvd.spotify.utils.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter @Setter @ToString
@XmlRootElement(name = "album")
@XmlAccessorType(XmlAccessType.FIELD)
public class Album {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    @XmlElement
    private Integer id;

    @XmlElement
    private String title;

    @XmlElement
    private MusicCreator musicCreator;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate releaseDate; // DATE requirement

    @XmlElementWrapper(name = "tracks")
    @XmlElement(name = "track")
    private List<Track> tracks;

    // default constructor required by JAXB
    public Album() {
        id = ID_GENERATOR.incrementAndGet();
    }
}
