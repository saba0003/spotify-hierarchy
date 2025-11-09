package com.solvd.spotify.models.commons;

import com.solvd.spotify.models.Album;
import com.solvd.spotify.models.Artist;
import com.solvd.spotify.models.Band;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor // default constructor required by JAXB
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Artist.class, Band.class})
public abstract class MusicCreator {

    @XmlElement
    protected Integer id;

    @XmlElement
    protected String name;

    @XmlElement
    protected Genre genre;

    @XmlElementWrapper(name = "albums")
    @XmlElement(name = "album")
    protected List<Album> albums;

}
