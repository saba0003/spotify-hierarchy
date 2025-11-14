package com.solvd.spotify.models;

import com.solvd.spotify.models.commons.MusicCreator;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlAccessType;

import java.util.List;

@XmlRootElement(name = "catalogue")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpotifyCatalogue {

    private Integer id;

    @XmlElementWrapper(name = "musicCreators")
    @XmlElements({
        @XmlElement(name = "artists", type = Artist.class),
        @XmlElement(name = "bands", type = Band.class)
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MusicCreator> getMusicCreators() {
        return musicCreators;
    }

    public void setMusicCreators(List<MusicCreator> musicCreators) {
        this.musicCreators = musicCreators;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "SpotifyCatalogue{" +
                "musicCreators=" + musicCreators +
                ", albums=" + albums +
                ", tracks=" + tracks +
                ", playlists=" + playlists +
                ", users=" + users +
                '}';
    }
}
