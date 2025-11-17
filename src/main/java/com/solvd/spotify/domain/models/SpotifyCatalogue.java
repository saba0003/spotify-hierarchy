package com.solvd.spotify.domain.models;

import com.solvd.spotify.domain.models.commons.MusicCreator;
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

    @XmlElementWrapper(name = "albumXmls")
    @XmlElement(name = "album")
    private List<AlbumXml> albumXmls;

    @XmlElementWrapper(name = "tracks")
    @XmlElement(name = "track")
    private List<Track> tracks;

    @XmlElementWrapper(name = "playlistXmls")
    @XmlElement(name = "playlist")
    private List<PlaylistXml> playlistXmls;

    @XmlElementWrapper(name = "userXmls")
    @XmlElement(name = "user")
    private List<UserXml> userXmls;

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

    public List<AlbumXml> getAlbums() {
        return albumXmls;
    }

    public void setAlbums(List<AlbumXml> albumXmls) {
        this.albumXmls = albumXmls;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<PlaylistXml> getPlaylists() {
        return playlistXmls;
    }

    public void setPlaylists(List<PlaylistXml> playlistXmls) {
        this.playlistXmls = playlistXmls;
    }

    public List<UserXml> getUsers() {
        return userXmls;
    }

    public void setUsers(List<UserXml> userXmls) {
        this.userXmls = userXmls;
    }

    @Override
    public String toString() {
        return "SpotifyCatalogue{" +
                "musicCreators=" + musicCreators +
                ", albumXmls=" + albumXmls +
                ", tracks=" + tracks +
                ", playlistXmls=" + playlistXmls +
                ", userXmls=" + userXmls +
                '}';
    }
}
