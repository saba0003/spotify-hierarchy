import com.solvd.spotify.models.Album;
import com.solvd.spotify.models.Playlist;
import com.solvd.spotify.models.Track;
import com.solvd.spotify.parsers.xml.AlbumsStaxParser;
import com.solvd.spotify.parsers.xml.PlaylistsStaxParser;
import com.solvd.spotify.parsers.xml.TracksStaxParser;
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
}
