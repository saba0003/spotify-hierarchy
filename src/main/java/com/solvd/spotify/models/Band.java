package com.solvd.spotify.models;

import com.solvd.spotify.models.commons.MusicCreator;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@NoArgsConstructor // default constructor required by JAXB
@XmlRootElement(name = "band")
@XmlAccessorType(XmlAccessType.FIELD)
public class Band extends MusicCreator {}
