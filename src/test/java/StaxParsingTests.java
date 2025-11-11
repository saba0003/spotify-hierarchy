import com.solvd.spotify.models.*;
import com.solvd.spotify.parsers.xml.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StaxParsingTests {

    @Test
    void tracksParseTest() {
        TracksStaxParser tracksStaxParser = new TracksStaxParser();
        List<Track> tracks = tracksStaxParser.parse("src/main/resources/raw/xml/tracks.xml", "src/main/resources/schemas/tracks-schema.xsd");
        Assertions.assertFalse(tracks.isEmpty());
    }

    @Test
    void albumsParseTest() {
        AlbumsStaxParser albumsStaxParser = new AlbumsStaxParser();
        List<Album> albums = albumsStaxParser.parse("src/main/resources/raw/xml/albums.xml", "src/main/resources/schemas/albums-schema.xsd");
        Assertions.assertFalse(albums.isEmpty());
    }

    @Test
    void playlistsParseTest() {
        PlaylistsStaxParser playlistsParser = new PlaylistsStaxParser();
        List<Playlist> playlists = playlistsParser.parse("src/main/resources/raw/xml/playlists.xml", "src/main/resources/schemas/playlists-schema.xsd");
        Assertions.assertFalse(playlists.isEmpty());
    }

    @Test
    void usersParseTest() {
        UsersStaxParser usersParser = new UsersStaxParser();
        List<User> users = usersParser.parse("src/main/resources/raw/xml/users.xml", "src/main/resources/schemas/users-schema.xsd");
        Assertions.assertFalse(users.isEmpty());
    }

    @Test
    void artistsParseTest() {
        MusicCreatorsStaxParser<Artist> artistsParser = new MusicCreatorsStaxParser<>(Artist.class);
        List<Artist> artists = artistsParser.parse("src/main/resources/raw/xml/artists.xml", "src/main/resources/schemas/artists-schema.xsd");
        Assertions.assertFalse(artists.isEmpty());
    }

    @Test
    void bandsParseTest() {
        MusicCreatorsStaxParser<Band> bandsParser = new MusicCreatorsStaxParser<>(Band.class);
        List<Band> bands = bandsParser.parse("src/main/resources/raw/xml/bands.xml", "src/main/resources/schemas/bands-schema.xsd");
        Assertions.assertFalse(bands.isEmpty());
    }
}
