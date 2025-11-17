import com.solvd.spotify.domain.models.*;
import com.solvd.spotify.parsers.xml.stax.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StaxParsingTests {

    @Test
    void tracksParseTest() {
        TracksStaxParser tracksStaxParser = new TracksStaxParser();
        List<Track> tracks = tracksStaxParser.parse("src/main/resources/raw/xml/tracks.xml", "src/main/resources/schemas/xsd/tracks-schema.xsd");
        Assertions.assertFalse(tracks.isEmpty());
    }

    @Test
    void albumsParseTest() {
        AlbumsStaxParser albumsStaxParser = new AlbumsStaxParser();
        List<AlbumXml> albums = albumsStaxParser.parse("src/main/resources/raw/xml/albums.xml", "src/main/resources/schemas/xsd/albums-schema.xsd");
        Assertions.assertFalse(albums.isEmpty());
    }

    @Test
    void playlistsParseTest() {
        PlaylistsStaxParser playlistsParser = new PlaylistsStaxParser();
        List<PlaylistXml> playlists = playlistsParser.parse("src/main/resources/raw/xml/playlists.xml", "src/main/resources/schemas/xsd/playlists-schema.xsd");
        Assertions.assertFalse(playlists.isEmpty());
    }

    @Test
    void usersParseTest() {
        UsersStaxParser usersParser = new UsersStaxParser();
        List<UserXml> usersXml = usersParser.parse("src/main/resources/raw/xml/users.xml", "src/main/resources/schemas/xsd/users-schema.xsd");
        Assertions.assertFalse(usersXml.isEmpty());
    }

    @Test
    void artistsParseTest() {
        MusicCreatorsStaxParser<Artist> artistsParser = new MusicCreatorsStaxParser<>(Artist.class);
        List<Artist> artists = artistsParser.parse("src/main/resources/raw/xml/artists.xml", "src/main/resources/schemas/xsd/artists-schema.xsd");
        Assertions.assertFalse(artists.isEmpty());
    }

    @Test
    void bandsParseTest() {
        MusicCreatorsStaxParser<Band> bandsParser = new MusicCreatorsStaxParser<>(Band.class);
        List<Band> bands = bandsParser.parse("src/main/resources/raw/xml/bands.xml", "src/main/resources/schemas/xsd/bands-schema.xsd");
        Assertions.assertFalse(bands.isEmpty());
    }
}
