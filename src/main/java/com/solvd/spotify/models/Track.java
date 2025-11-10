package com.solvd.spotify.models;

import com.solvd.spotify.utils.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Getter @Setter @ToString
@XmlRootElement(name = "track")
@XmlAccessorType(XmlAccessType.FIELD)
public class Track {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    @XmlElement
    private Integer id;

    @XmlElement
    private String title;

    @XmlElement
    private int durationSeconds;

    @XmlElement
    private boolean explicit;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime lastPlayedAt;

    // default constructor required by JAXB
    public Track() {
        id = ID_GENERATOR.incrementAndGet();
    }

}
