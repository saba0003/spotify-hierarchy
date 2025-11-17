package com.solvd.spotify.domain.models;

public class PlaylistTrack {

    private Integer playlistId;
    private Integer trackId;

    public Integer getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    @Override
    public String toString() {
        return "PlaylistTrack{" +
                "playlistId=" + playlistId +
                ", trackId=" + trackId +
                '}';
    }
}
