package com.solvd.spotify.parsers.xml;

import com.solvd.spotify.models.User;
import com.solvd.spotify.models.commons.Subscription;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;

public class UsersStaxParser extends StaxParser<User> {

    @Override
    protected String collectionTag() {
        return "users";
    }

    @Override
    protected String itemTag() {
        return "user";
    }

    @Override
    protected User createItem() {
        return new User();
    }

    @Override
    protected void handleStartElement(String tag, XMLEventReader reader, User user) throws XMLStreamException {
        switch (tag) {
            case "username" -> setUsername(reader, user);
            case "subscription" -> setSubscription(reader, user);
            case null, default -> throw new IllegalStateException("Program shouldn't be here!"); // TODO: check this out
        }
    }

    @Override
    protected void handleEndElement(String tag, XMLEventReader reader, User user) throws XMLStreamException {
        if ("subscription".equals(tag))
            setPlaylists(reader, user);
        // TODO: Exception handling can be added for illegal states
    }

    private void setUsername(XMLEventReader reader, User user) throws XMLStreamException {
        user.setUsername(readText(reader));
    }

    private void setSubscription(XMLEventReader reader, User user) throws XMLStreamException {
        user.setSubscription(readEnum(reader, Subscription.class));
    }

    private void setPlaylists(XMLEventReader reader, User user) throws XMLStreamException {
        user.setPlaylists(new PlaylistsStaxParser().parseCollection(reader));
    }
}
