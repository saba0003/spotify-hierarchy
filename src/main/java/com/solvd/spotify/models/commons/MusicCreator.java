package com.solvd.spotify.models.commons;

import com.solvd.spotify.models.Album;
import com.solvd.spotify.models.Artist;
import com.solvd.spotify.models.Band;
import com.solvd.spotify.parsers.xml.jaxb.adapters.GenreAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Artist.class, Band.class})
public abstract class MusicCreator {

    protected Integer id;

    @XmlElement
    protected String name;

    @XmlElement(name = "genre")
    @XmlJavaTypeAdapter(GenreAdapter.class)
    protected Genre genre;

    @XmlElementWrapper(name = "albums")
    @XmlElement(name = "album")
    protected List<Album> albums;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        return "MusicCreator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", albums=" + albums +
                '}';
    }
}
