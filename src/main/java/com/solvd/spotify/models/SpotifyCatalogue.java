package com.solvd.spotify.models;

import com.solvd.spotify.models.commons.MusicCreator;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlAccessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor // default constructor required by JAXB
@XmlRootElement(name = "catalog")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpotifyCatalogue {

    @XmlElementWrapper(name = "musicCreators")
    @XmlElements({
        @XmlElement(name = "bands", type = Band.class),
        @XmlElement(name = "artists", type = Artist.class)
    })
    private List<MusicCreator> musicCreators;

    @XmlElementWrapper(name = "albums")
    @XmlElement(name = "album")
    private List<Album> albums;

    @XmlElementWrapper(name = "tracks")
    @XmlElement(name = "track")
    private List<Track> tracks;

    @XmlElementWrapper(name = "playlists")
    @XmlElement(name = "playlist")
    private List<Playlist> playlists;

    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private List<User> users;

}
