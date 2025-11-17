package com.solvd.spotify.parsers.xml.stax;

import com.solvd.spotify.domain.models.UserXml;
import com.solvd.spotify.domain.models.commons.Subscription;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;

public class UsersStaxParser extends StaxParser<UserXml> {

    @Override
    protected String collectionTag() {
        return "users";
    }

    @Override
    protected String itemTag() {
        return "user";
    }

    @Override
    protected UserXml createItem() {
        return new UserXml();
    }

    @Override
    protected void handleStartElement(String tag, XMLEventReader reader, UserXml userXml) throws XMLStreamException {
        switch (tag) {
            case "username" -> setUsername(reader, userXml);
            case "subscription" -> setSubscription(reader, userXml);
            case null, default -> throw new IllegalStateException("Program shouldn't be here!"); // TODO: check this out
        }
    }

    @Override
    protected void handleEndElement(String tag, XMLEventReader reader, UserXml userXml) throws XMLStreamException {
        if ("subscription".equals(tag))
            setPlaylists(reader, userXml);
        // TODO: Exception handling can be added for illegal states
    }

    private void setUsername(XMLEventReader reader, UserXml userXml) throws XMLStreamException {
        userXml.setUsername(readText(reader));
    }

    private void setSubscription(XMLEventReader reader, UserXml userXml) throws XMLStreamException {
        userXml.setSubscription(readEnum(reader, Subscription.class));
    }

    private void setPlaylists(XMLEventReader reader, UserXml userXml) throws XMLStreamException {
        userXml.setPlaylists(new PlaylistsStaxParser().parseCollection(reader));
    }
}
