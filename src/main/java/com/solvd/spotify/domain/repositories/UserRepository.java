package com.solvd.spotify.domain.repositories;

import com.solvd.spotify.domain.models.User;
import com.solvd.spotify.utils.DataSourceProvider;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements JdbcRepository<User, Integer> {

    private final DataSource ds = DataSourceProvider.getDataSource();

    @Override
    public User findById(Integer id) {
        String sql = "SELECT id, username FROM users WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("username"));
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT id, username FROM users";
        List<User> out = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(new User(rs.getInt("id"), rs.getString("username")));
            return out;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO users (username) VALUES (?)";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    conn.commit();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET username = ? WHERE id = ?";
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, user.getUsername());
                ps.setInt(2, user.getId());
                ps.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void reportFullCatalogueForUser(Integer userId) {
        String sql = """
                SELECT u.id AS user_id, u.username, u.subscription AS subscription_type,
                       p.id AS playlist_id, p.title AS playlist_title,
                       t.id AS track_id, t.title AS track_title, t.duration_seconds,
                       mc.id AS creator_id, mc.name AS creator_name, mc.genre,
                       a.id AS album_id, a.title AS album_title, a.release_date
                    FROM users u
                        JOIN playlists p ON u.id = p.user_id
                        JOIN playlist_tracks pt ON p.id = pt.playlist_id
                        JOIN tracks t ON pt.track_id = t.id
                        JOIN album_tracks at ON t.id = at.track_id
                        JOIN albums a ON at.album_id = a.id
                        JOIN music_creators mc ON a.creator_id = mc.id;
                """;

        List<FullUserCatalogueDTO> results = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FullUserCatalogueDTO dto = new FullUserCatalogueDTO();
                    dto.setUserId(rs.getInt("user_id"));
                    dto.setUsername(rs.getString("username"));
                    dto.setSubscription(rs.getString("subscription_type"));
                    dto.setPlaylistId(rs.getObject("playlist_id") != null ? rs.getInt("playlist_id") : null);
                    dto.setPlaylistTitle(rs.getString("playlist_title"));
                    dto.setTrackId(rs.getObject("track_id") != null ? rs.getInt("track_id") : null);
                    dto.setTrackTitle(rs.getString("track_title"));
                    dto.setAlbumId(rs.getObject("album_id") != null ? rs.getInt("album_id") : null);
                    dto.setAlbumTitle(rs.getString("album_title"));
                    dto.setCreatorId(rs.getObject("creator_id") != null ? rs.getInt("creator_id") : null);
                    dto.setCreatorName(rs.getString("creator_name"));
                    results.add(dto);
                }
            }
            System.out.println(results);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static class FullUserCatalogueDTO {

        private Integer userId;
        private String username;
        private String subscription;
        private Integer playlistId;
        private String playlistTitle;
        private Integer trackId;
        private String trackTitle;
        private Integer albumId;
        private String albumTitle;
        private Integer creatorId;
        private String creatorName;
        private String genre;

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setSubscription(String subscription) {
            this.subscription = subscription;
        }

        public void setPlaylistId(Integer playlistId) {
            this.playlistId = playlistId;
        }

        public void setPlaylistTitle(String playlistTitle) {
            this.playlistTitle = playlistTitle;
        }

        public void setTrackId(Integer trackId) {
            this.trackId = trackId;
        }

        public void setTrackTitle(String trackTitle) {
            this.trackTitle = trackTitle;
        }

        public void setAlbumId(Integer albumId) {
            this.albumId = albumId;
        }

        public void setAlbumTitle(String albumTitle) {
            this.albumTitle = albumTitle;
        }

        public void setCreatorId(Integer creatorId) {
            this.creatorId = creatorId;
        }

        public void setCreatorName(String creatorName) {
            this.creatorName = creatorName;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        @Override
        public String toString() {
            return "FullUserCatalogueDTO{" +
                    "userId=" + userId +
                    ", username='" + username + '\'' +
                    ", subscription='" + subscription + '\'' +
                    ", playlistId=" + playlistId +
                    ", playlistTitle='" + playlistTitle + '\'' +
                    ", trackId=" + trackId +
                    ", trackTitle='" + trackTitle + '\'' +
                    ", albumId=" + albumId +
                    ", albumTitle='" + albumTitle + '\'' +
                    ", creatorId=" + creatorId +
                    ", creatorName='" + creatorName + '\'' +
                    ", genre='" + genre + '\'' +
                    '}';
        }
    }
}
