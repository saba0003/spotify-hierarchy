CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS subscriptions (
    id SERIAL PRIMARY KEY,
    type TEXT NOT NULL,
    start_date DATE,
    end_date DATE,
    user_id INT NOT NULL,
    CONSTRAINT fk_user_subscription FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS playlists (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    user_id INT,
    CONSTRAINT fk_user_playlist FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS tracks (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    duration_seconds INT NOT NULL,
    explicit BOOLEAN NOT NULL,
    last_played_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS playlist_tracks (
    playlist_id INT NOT NULL,
    track_id INT NOT NULL,
    PRIMARY KEY (playlist_id, track_id),
    CONSTRAINT fk_playlist FOREIGN KEY (playlist_id) REFERENCES playlists(id),
    CONSTRAINT fk_track FOREIGN KEY (track_id) REFERENCES tracks(id)
);

CREATE TABLE IF NOT EXISTS music_creators (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    genre TEXT
);

CREATE TABLE IF NOT EXISTS artists (
    id INT PRIMARY KEY,
    CONSTRAINT fk_artist_mc FOREIGN KEY (id) REFERENCES music_creators(id)
);

CREATE TABLE IF NOT EXISTS bands (
    id INT PRIMARY KEY,
    CONSTRAINT fk_band_mc FOREIGN KEY (id) REFERENCES music_creators(id)
);

CREATE TABLE IF NOT EXISTS albums (
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    release_date DATE,
    creator_id INT NOT NULL,
    CONSTRAINT fk_album_creator FOREIGN KEY (creator_id) REFERENCES music_creators(id)
);

CREATE TABLE IF NOT EXISTS album_tracks (
    album_id INT NOT NULL,
    track_id INT NOT NULL,
    PRIMARY KEY (album_id, track_id),
    CONSTRAINT fk_album FOREIGN KEY (album_id) REFERENCES albums(id),
    CONSTRAINT fk_album_track FOREIGN KEY (track_id) REFERENCES tracks(id)
);

CREATE TABLE IF NOT EXISTS catalogues (
    id SERIAL PRIMARY KEY,
    name TEXT DEFAULT 'Main Catalogue'
);

CREATE TABLE IF NOT EXISTS catalogue_music_creators (
    catalogue_id INT NOT NULL,
    creator_id INT NOT NULL,
    PRIMARY KEY (catalogue_id, creator_id),
    CONSTRAINT fk_catalogue FOREIGN KEY (catalogue_id) REFERENCES catalogues(id),
    CONSTRAINT fk_creator FOREIGN KEY (creator_id) REFERENCES music_creators(id)
);
