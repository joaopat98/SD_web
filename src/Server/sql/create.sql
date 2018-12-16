CREATE TABLE app_user
(
  username      VARCHAR(50)  NOT NULL,
  pw_hash       VARCHAR(512) NOT NULL,
  dropbox_token VARCHAR(512),
  is_editor     BOOL         NOT NULL,
  PRIMARY KEY (username)
);

CREATE TABLE artist
(
  artist_id   SERIAL,
  name        VARCHAR(512) NOT NULL,
  description VARCHAR(512),
  PRIMARY KEY (artist_id)
);

CREATE TABLE album
(
  album_id    SERIAL,
  name        VARCHAR(512) NOT NULL UNIQUE,
  description VARCHAR(512),
  artist_id   BIGINT       NOT NULL,
  label_id    BIGINT,
  PRIMARY KEY (album_id)
);

CREATE TABLE song
(
  song_id         SERIAL,
  name            VARCHAR(512) NOT NULL,
  genre           VARCHAR(512),
  lyrics          VARCHAR(512),
  length          BIGINT,
  lyric_writer_id BIGINT,
  PRIMARY KEY (song_id)
);

CREATE TABLE playlist
(
  owner_username VARCHAR(50)  NOT NULL,
  playlist_id    SERIAL,
  name           VARCHAR(512) NOT NULL,
  is_public      BOOL         NOT NULL,
  PRIMARY KEY (playlist_id)
);

CREATE TABLE playlist_song
(
  owner_username VARCHAR(50) NOT NULL,
  position       SMALLINT    NOT NULL,
  playlist_id    BIGINT,
  song_id        BIGINT,
  PRIMARY KEY (playlist_id, song_id)
);

CREATE TABLE song_editor
(
  editor_username VARCHAR(50),
  song_id         BIGINT,
  PRIMARY KEY (editor_username, song_id)
);

CREATE TABLE artist_editor
(
  editor_username VARCHAR(50),
  artist_id       BIGINT,
  PRIMARY KEY (editor_username, artist_id)
);

CREATE TABLE album_editor
(
  editor_username VARCHAR(50),
  artist_id       BIGINT NOT NULL,
  album_id        BIGINT,
  PRIMARY KEY (editor_username, album_id)
);

CREATE TABLE file
(
  dropbox_id VARCHAR(50),
  song_id          BIGINT,
  username VARCHAR(50),
  PRIMARY KEY (song_id, creator_username)
);

CREATE TABLE review
(
  score             SMALLINT     NOT NULL,
  detail            VARCHAR(512) NOT NULL,
  album_id          BIGINT,
  artist_id         BIGINT       NOT NULL,
  reviewer_username VARCHAR(50),
  PRIMARY KEY (album_id, reviewer_username)
);

CREATE TABLE album_notification
(
  album_id          BIGINT,
  artist_id         BIGINT NOT NULL,
  editor_username   VARCHAR(50),
  receiver_username VARCHAR(50),
  PRIMARY KEY (album_id, editor_username, receiver_username)
);

CREATE TABLE artist_notification
(
  artist_id         BIGINT,
  editor_username   VARCHAR(50),
  receiver_username VARCHAR(50),
  PRIMARY KEY (artist_id, editor_username, receiver_username)
);

CREATE TABLE song_notification
(
  song_id           BIGINT,
  editor_username   VARCHAR(50),
  receiver_username VARCHAR(50),
  PRIMARY KEY (song_id, editor_username, receiver_username)
);

CREATE TABLE editor_notification
(
  editor_username   VARCHAR(50),
  receiver_username VARCHAR(50),
  PRIMARY KEY (editor_username, receiver_username)
);

CREATE TABLE label
(
  label_id    SERIAL,
  name        VARCHAR(512) NOT NULL,
  description VARCHAR(512),
  PRIMARY KEY (label_id)
);

CREATE TABLE studio
(
  studio_id   SERIAL,
  name        VARCHAR(512) NOT NULL,
  description VARCHAR(512),
  PRIMARY KEY (studio_id)
);

CREATE TABLE musician
(
  musician_id SERIAL,
  name        VARCHAR(512) NOT NULL,
  description VARCHAR(512),
  PRIMARY KEY (musician_id)
);

CREATE TABLE musician_role
(
  role        VARCHAR(512),
  musician_id BIGINT,
  artist_id   BIGINT,
  PRIMARY KEY (musician_id, artist_id)
);

CREATE TABLE song_composer
(
  song_id     BIGINT,
  musician_id BIGINT,
  PRIMARY KEY (song_id, musician_id)
);

CREATE TABLE album_studio
(
  album_id  BIGINT,
  artist_id BIGINT NOT NULL,
  studio_id BIGINT,
  PRIMARY KEY (album_id, studio_id)
);

CREATE TABLE song_album
(
  song_id   BIGINT,
  album_id  BIGINT,
  artist_id BIGINT NOT NULL,
  track_num SMALLINT,
  PRIMARY KEY (song_id, album_id)
);

ALTER TABLE album
  ADD CONSTRAINT album_artist FOREIGN KEY (artist_id) REFERENCES artist (artist_id);
ALTER TABLE album
  ADD CONSTRAINT album_label FOREIGN KEY (label_id) REFERENCES label (label_id);
ALTER TABLE song
  ADD CONSTRAINT song_lyrics FOREIGN KEY (lyric_writer_id) REFERENCES musician (musician_id);
ALTER TABLE playlist
  ADD CONSTRAINT playlist_user FOREIGN KEY (owner_username) REFERENCES app_user (username);
ALTER TABLE playlist_song
  ADD CONSTRAINT playlist_song_song FOREIGN KEY (song_id) REFERENCES song (song_id);
ALTER TABLE playlist_song
  ADD CONSTRAINT playlist_song_playlist FOREIGN KEY (playlist_id) REFERENCES playlist (playlist_id);
ALTER TABLE file
  ADD CONSTRAINT file_song FOREIGN KEY (song_id) REFERENCES song (song_id);
ALTER TABLE file
  ADD CONSTRAINT file_creator FOREIGN KEY (username) REFERENCES app_user (username);
ALTER TABLE review
  ADD CONSTRAINT review_album FOREIGN KEY (album_id) REFERENCES album (album_id);
ALTER TABLE review
  ADD CONSTRAINT review_artist FOREIGN KEY (artist_id) REFERENCES artist (artist_id);
ALTER TABLE review
  ADD CONSTRAINT review_creator FOREIGN KEY (reviewer_username) REFERENCES app_user (username);
ALTER TABLE album_notification
  ADD CONSTRAINT album_notification_album FOREIGN KEY (album_id) REFERENCES album (album_id);
ALTER TABLE album_notification
  ADD CONSTRAINT album_notification_artist FOREIGN KEY (artist_id) REFERENCES artist (artist_id);
ALTER TABLE album_notification
  ADD CONSTRAINT album_notification_editor FOREIGN KEY (editor_username) REFERENCES app_user (username);
ALTER TABLE album_notification
  ADD CONSTRAINT album_notification_receiver FOREIGN KEY (receiver_username) REFERENCES app_user (username);
ALTER TABLE artist_notification
  ADD CONSTRAINT artist_notification_artist FOREIGN KEY (artist_id) REFERENCES artist (artist_id);
ALTER TABLE artist_notification
  ADD CONSTRAINT artist_notification_editor FOREIGN KEY (editor_username) REFERENCES app_user (username);
ALTER TABLE artist_notification
  ADD CONSTRAINT artist_notification_receiver FOREIGN KEY (receiver_username) REFERENCES app_user (username);
ALTER TABLE editor_notification
  ADD CONSTRAINT editor_notification_editor FOREIGN KEY (editor_username) REFERENCES app_user (username);
ALTER TABLE editor_notification
  ADD CONSTRAINT editor_notification_receiver FOREIGN KEY (receiver_username) REFERENCES app_user (username);
ALTER TABLE song_notification
  ADD CONSTRAINT song_notification_song FOREIGN KEY (song_id) REFERENCES song (song_id);
ALTER TABLE song_notification
  ADD CONSTRAINT song_notification_editor FOREIGN KEY (editor_username) REFERENCES app_user (username);
ALTER TABLE song_notification
  ADD CONSTRAINT song_notification_receiver FOREIGN KEY (receiver_username) REFERENCES app_user (username);
ALTER TABLE musician_role
  ADD CONSTRAINT musician_role_musician FOREIGN KEY (musician_id) REFERENCES musician (musician_id);
ALTER TABLE musician_role
  ADD CONSTRAINT musician_role_artist FOREIGN KEY (artist_id) REFERENCES artist (artist_id);
ALTER TABLE song_composer
  ADD CONSTRAINT song_composer_song FOREIGN KEY (song_id) REFERENCES song (song_id);
ALTER TABLE song_composer
  ADD CONSTRAINT song_composer_composer FOREIGN KEY (musician_id) REFERENCES musician (musician_id);
ALTER TABLE album_studio
  ADD CONSTRAINT album_studio_album FOREIGN KEY (album_id) REFERENCES album (album_id);
ALTER TABLE album_studio
  ADD CONSTRAINT album_studio_artist FOREIGN KEY (artist_id) REFERENCES artist (artist_id);
ALTER TABLE album_studio
  ADD CONSTRAINT album_studio_studio FOREIGN KEY (studio_id) REFERENCES studio (studio_id);
ALTER TABLE song_album
  ADD CONSTRAINT song_album_song FOREIGN KEY (song_id) REFERENCES song (song_id);
ALTER TABLE song_album
  ADD CONSTRAINT song_album_album FOREIGN KEY (album_id) REFERENCES album (album_id);
ALTER TABLE song_album
  ADD CONSTRAINT song_album_artist FOREIGN KEY (artist_id) REFERENCES artist (artist_id);
ALTER TABLE song_editor
  ADD CONSTRAINT song_editor_user FOREIGN KEY (editor_username) REFERENCES app_user (username);
ALTER TABLE song_editor
  ADD CONSTRAINT song_editor_song FOREIGN KEY (song_id) REFERENCES song (song_id);
ALTER TABLE album_editor
  ADD CONSTRAINT album_editor_user FOREIGN KEY (editor_username) REFERENCES app_user (username);
ALTER TABLE album_editor
  ADD CONSTRAINT album_editor_artist FOREIGN KEY (artist_id) REFERENCES artist (artist_id);
ALTER TABLE album_editor
  ADD CONSTRAINT album_editor_album FOREIGN KEY (album_id) REFERENCES album (album_id);
ALTER TABLE artist_editor
  ADD CONSTRAINT artist_editor_user FOREIGN KEY (editor_username) REFERENCES app_user (username);
ALTER TABLE artist_editor
  ADD CONSTRAINT artist_editor_artist FOREIGN KEY (artist_id) REFERENCES artist (artist_id);