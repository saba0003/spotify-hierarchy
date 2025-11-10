package com.solvd.spotify.models;

import com.solvd.spotify.models.commons.Subscription;
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
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    @XmlElement
    private Integer id;

    @XmlElement
    private String username;

    @XmlElementWrapper(name = "playlists")
    @XmlElement(name = "playlist")
    private List<Playlist> playlists;

    @XmlElement
    private Subscription subscription;

    // default constructor required by JAXB
    public User() {
        id = ID_GENERATOR.incrementAndGet();
    }

}
