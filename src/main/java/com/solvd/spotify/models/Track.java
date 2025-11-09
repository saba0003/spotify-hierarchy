package com.solvd.spotify.models;

import com.solvd.spotify.utils.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor // default constructor required by JAXB
@XmlRootElement(name = "track")
@XmlAccessorType(XmlAccessType.FIELD)
public class Track {

    @XmlElement
    private Integer id;

    @XmlElement
    private String title;

    @XmlElement
    private int durationSeconds; // NUMBER requirement

    @XmlElement
    private boolean explicit; // BOOLEAN requirement

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime lastPlayedAt; // DATETIME requirement

}
