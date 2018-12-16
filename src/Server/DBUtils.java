package Server;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
    QueryExecutor q;

    public DBUtils(QueryExecutor q) {
        this.q = q;
    }

    public boolean artistExists(String artist_id) throws SQLException {
        ResultSet rs = q.executeQuery("SELECT exists(SELECT 1 " +
                "from artist " +
                "WHERE artist_id = " + artist_id + ")");
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean albumExists(String album_id) throws SQLException {
        ResultSet rs = q.executeQuery("SELECT exists(SELECT 1 " +
                "from album " +
                "WHERE album_id = " + album_id + ");");
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean songExists(String song_id) throws SQLException {
        ResultSet rs = q.executeQuery("SELECT exists(SELECT 1 " +
                "from song " +
                "WHERE song_id = " + song_id + ")");
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean musicianExists(String musician_id) throws SQLException {
        ResultSet rs = q.executeQuery("SELECT exists(SELECT 1 " +
                "from musician " +
                "WHERE musician_id = " + musician_id + ")");
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean studioExists(String studio_id) throws SQLException {
        ResultSet rs = q.executeQuery("SELECT exists(SELECT 1 " +
                "from studio " +
                "WHERE studio_id = " + studio_id + ")");
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean labelExists(String label_id) throws SQLException {
        ResultSet rs = q.executeQuery("SELECT exists(SELECT 1 " +
                "from label " +
                "WHERE label_id = " + label_id + ")");
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean wasRecordedIn(String studio_id, String album_id) throws SQLException {
        ResultSet rs = q.executeQuery(
                "SELECT exists(SELECT 1 " +
                        "FROM album_studio " +
                        "WHERE studio_id = " + studio_id + " " +
                        "AND album_id = " + album_id + ");"
        );
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean isComposer(String musician_id, String song_id) throws SQLException {
        ResultSet rs = q.executeQuery(
                "SELECT exists(SELECT 1 " +
                        "FROM song_composer " +
                        "WHERE musician_id = " + musician_id + " " +
                        "AND song_id = " + song_id + ");"
        );
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean musicianIsInArtist(String musician_id, String artist_id) throws SQLException {
        ResultSet rs = q.executeQuery(
                "SELECT exists(SELECT 1 " +
                        "FROM musician_role " +
                        "WHERE musician_id = " + musician_id + " " +
                        "AND artist_id = " + artist_id + ");"
        );
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean userExists(String username) throws SQLException {
        ResultSet rs = q.executeQuery(
                "SELECT exists(SELECT 1 " +
                        "FROM app_user " +
                        "WHERE username = '" + username + "');"
        );
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean playlistExists(String username, String playlist_id) throws SQLException {
        ResultSet rs = q.executeQuery(
                "SELECT exists(SELECT 1 " +
                        "FROM playlist " +
                        "WHERE playlist_id = '" + playlist_id + "' " +
                        "AND owner_username = '" + username + "');"
        );
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean hasReviewed(String username, String album_id) throws SQLException {
        ResultSet rs = q.executeQuery(
                "SELECT exists(SELECT 1 " +
                        "FROM album_review " +
                        "WHERE album_id = " + album_id + " " +
                        "AND reviewer_username = '" + username + "');"
        );
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean existsInPlaylist(String username, String playlist_id, String song_id) throws SQLException {
        ResultSet rs = q.executeQuery(
                "SELECT exists(SELECT 1 " +
                        "FROM playlist_song " +
                        "WHERE song_id = '" + song_id + "' " +
                        "AND playlist_id = (" +
                        "SELECT playlist_id " +
                        "FROM playlist " +
                        "WHERE playlist_id = " + playlist_id + " " +
                        "AND owner_username = '" + username + "'));"
        );
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean existsInAlbum(String album_id, String song_id) throws SQLException {
        ResultSet rs = q.executeQuery(
                "SELECT exists(SELECT 1 " +
                        "FROM song_album " +
                        "WHERE album_id = " + album_id + " " +
                        "AND song_id = " + song_id + ");"
        );
        rs.next();
        return rs.getBoolean(1);
    }

    public boolean isEditor(String username) throws SQLException {
        ResultSet rs = q.executeQuery(
                "SELECT is_editor " +
                        "FROM app_user " +
                        "WHERE username = '" + username + "';"
        );
        rs.next();
        return rs.getBoolean(1);
    }

}
