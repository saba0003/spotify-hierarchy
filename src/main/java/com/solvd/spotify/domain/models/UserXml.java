package com.solvd.spotify.domain.models;

import com.solvd.spotify.domain.models.commons.Subscription;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserXml {

    private Integer id;

    @XmlElement
    private String username;

    @XmlElementWrapper(name = "playlist")
    @XmlElement(name = "playlist")
    private List<PlaylistXml> playlistsXml;

    @XmlElement
    private Subscription subscription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<PlaylistXml> getPlaylists() {
        return playlistsXml;
    }

    public void setPlaylists(List<PlaylistXml> playlistXmls) {
        this.playlistsXml = playlistXmls;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public String toString() {
        return "UserXml{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", playlistsXml=" + playlistsXml +
                ", subscription=" + subscription +
                '}';
    }
}
