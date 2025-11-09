package com.solvd.spotify.models;

import com.solvd.spotify.models.commons.Subscription;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor // default constructor required by JAXB
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    @XmlElement
    private Integer id;

    @XmlElement
    private String username;

    @XmlElementWrapper(name = "playlists")
    @XmlElement(name = "playlist")
    private List<Playlist> playlists;

    @XmlElement
    private Subscription subscription;

}
