package com.solvd.spotify.domain.models;

import com.solvd.spotify.domain.models.commons.MusicCreator;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "band")
@XmlAccessorType(XmlAccessType.FIELD)
public class Band extends MusicCreator {

    @Override
    public String toString() {
        return "Band{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", albumXmls=" + albumXmls +
                '}';
    }
}
