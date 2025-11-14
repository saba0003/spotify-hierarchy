INSERT INTO users (username) VALUES ('Alice');
INSERT INTO users (username) VALUES ('Bob');
INSERT INTO users (username) VALUES ('Charlie');
INSERT INTO subscriptions (type, start_date, end_date, user_id) VALUES ('Premium', '2025-01-01', '2025-12-31', 1);
INSERT INTO subscriptions (type, start_date, end_date, user_id) VALUES ('Free', '2025-03-01', '2025-06-30', 2);
INSERT INTO music_creators (name, genre) VALUES ('The Weeknd', 'Pop');
INSERT INTO music_creators (name, genre) VALUES ('Linkin Park', 'Rock');
INSERT INTO albums (title, release_date, creator_id) VALUES ('Starboy', '2016-11-25', 1);
INSERT INTO albums (title, release_date, creator_id) VALUES ('Hybrid Theory', '2000-10-24', 2);
INSERT INTO tracks (title, duration_seconds, explicit, last_played_at) VALUES ('Starboy', 274, true, '2025-11-20 23:00:00');

UPDATE users SET username = 'Alice M' WHERE id = 1;
UPDATE users SET username = 'Bob B' WHERE id = 2;
UPDATE subscriptions SET type = 'Premium Plus' WHERE id = 1;
UPDATE subscriptions SET end_date = '2025-07-01' WHERE id = 2;
UPDATE music_creators SET genre = 'R&B' WHERE id = 1;
UPDATE music_creators SET name = 'Linkin Park Updated' WHERE id = 2;
UPDATE albums SET title = 'Starboy Deluxe' WHERE id = 1;
UPDATE albums SET release_date = '2000-11-01' WHERE id = 2;
UPDATE tracks SET explicit = false WHERE id = 1;
UPDATE tracks SET duration_seconds = 280 WHERE id = 1;

DELETE FROM tracks WHERE id = 1;
DELETE FROM albums WHERE id = 1;
DELETE FROM music_creators WHERE id = 1;
DELETE FROM subscriptions WHERE id = 2;
DELETE FROM users WHERE id = 3;
DELETE FROM playlist_tracks WHERE playlist_id = 1 AND track_id = 1;
DELETE FROM catalogue_music_creators WHERE catalogue_id = 1 AND creator_id = 2;
DELETE FROM playlists WHERE id = 1;
DELETE FROM bands WHERE id = 2;
DELETE FROM artists WHERE id = 1;

ALTER TABLE users ADD COLUMN email VARCHAR(255) UNIQUE;
ALTER TABLE tracks ADD COLUMN bitrate INT DEFAULT 320;
ALTER TABLE albums ADD COLUMN cover_url VARCHAR(500);
ALTER TABLE playlists ADD COLUMN description TEXT;
ALTER TABLE subscriptions ADD COLUMN status VARCHAR(20) DEFAULT 'active';

SELECT u.id AS user_id, u.username, s.type AS subscription_type,
       p.id AS playlist_id, p.title AS playlist_title,
       t.id AS track_id, t.title AS track_title, t.duration_seconds,
       mc.id AS creator_id, mc.name AS creator_name, mc.genre,
       a.id AS album_id, a.title AS album_title, a.release_date
    FROM users u
        JOIN subscriptions s ON u.id = s.user_id
        JOIN playlists p ON u.id = p.user_id
        JOIN playlist_tracks pt ON p.id = pt.playlist_id
        JOIN tracks t ON pt.track_id = t.id
        JOIN albums a ON t.id IN (SELECT track_id FROM album_tracks WHERE album_id = a.id)
        JOIN music_creators mc ON a.creator_id = mc.id;

SELECT u.username, s.type FROM users u INNER JOIN subscriptions s ON u.id = s.user_id;
SELECT u.username, s.type FROM users u LEFT JOIN subscriptions s ON u.id = s.user_id;
SELECT u.username, s.type FROM users u RIGHT JOIN subscriptions s ON u.id = s.user_id;
SELECT u.username, s.type FROM users u LEFT JOIN subscriptions s ON u.id = s.user_id
    UNION
SELECT u.username, s.type FROM users u RIGHT JOIN subscriptions s ON u.id = s.user_id;
SELECT p.title AS playlist, t.title AS track FROM playlists p
    JOIN playlist_tracks pt ON p.id = pt.playlist_id
    JOIN tracks t ON pt.track_id = t.id;

SELECT COUNT(*) AS user_count FROM users;
SELECT user_id, COUNT(*) AS playlists_count FROM playlists GROUP BY user_id;
SELECT album_id, COUNT(track_id) AS track_count FROM album_tracks GROUP BY album_id;
SELECT AVG(duration_seconds) AS avg_duration FROM tracks;
SELECT MAX(duration_seconds) AS max_duration FROM tracks;
SELECT MIN(duration_seconds) AS min_duration FROM tracks;
SELECT playlist_id, SUM(t.duration_seconds) AS total_duration FROM playlist_tracks pt
    JOIN tracks t ON pt.track_id = t.id GROUP BY playlist_id;

SELECT user_id, COUNT(*) AS playlists_count FROM playlists GROUP BY user_id HAVING COUNT(*) > 1;
SELECT a.id AS album_id, COUNT(t.id) AS long_tracks FROM albums a
    JOIN album_tracks at ON a.id = at.album_id
    JOIN tracks t ON at.track_id = t.id
    GROUP BY a.id
    HAVING COUNT(t.id) > 0;
SELECT creator_id, COUNT(*) AS album_count FROM albums GROUP BY creator_id HAVING COUNT(*) > 1;
SELECT playlist_id, COUNT(track_id) AS track_count FROM playlist_tracks GROUP BY playlist_id HAVING COUNT(track_id) > 2;
SELECT a.id AS album_id, AVG(t.duration_seconds) AS avg_duration FROM albums a
    JOIN album_tracks at ON a.id = at.album_id
    JOIN tracks t ON at.track_id = t.id
    GROUP BY a.id
    HAVING AVG(t.duration_seconds) > 250;
SELECT type, COUNT(*) AS cnt FROM subscriptions GROUP BY type HAVING COUNT(*) > 0;
SELECT u.id, COUNT(s.id) AS active_subs FROM users u
    JOIN subscriptions s ON u.id = s.user_id
    GROUP BY u.id
    HAVING COUNT(s.id) > 0;

