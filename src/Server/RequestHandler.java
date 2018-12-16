package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class RequestHandler extends Thread {
    private String request;
    private final MulticastSocket socket;
    private String multiCastAddress;
    private int multiCastPort;
    private String TCPAddress;
    private int TCPPort;
    private String pref_id;
    private String server_id;
    private QueryExecutor q;
    private DBUtils util;


    public RequestHandler(String request, MulticastSocket socket, String multiCastAddress, int multiCastPort,
                          String TCPAddress, int TCPPort, String server_id) throws SQLException {
        this.request = request;
        this.socket = socket;
        this.multiCastAddress = multiCastAddress;
        this.multiCastPort = multiCastPort;
        this.TCPAddress = TCPAddress;
        this.TCPPort = TCPPort;
        this.pref_id = UUID.randomUUID().toString();
        this.server_id = server_id;
        this.q = QueryExecutor.newConnection();
        util = new DBUtils(q);
    }

    @Override
    public void run() {
        String response = "";
        System.out.println("received packet");
        HashMap<String, String> props = Dictionary.mainDict(request);
        if (props.containsKey("action")) {
            if (!props.containsKey("pref_server") || props.get("pref_server").equals(pref_id)) {
                switch (props.get("action")) {
                    case "provide_id":
                        response = "type:provide_id;id:" + server_id;
                        break;
                    case "register":
                        response = register(props);
                        break;
                    case "login":
                        response = login(props);
                        break;
                    case "add_song":
                        response = addSong(props);
                        break;
                    case "add_composer_to_song":
                        response = addComposerToSong(props);
                        break;
                    case "add_album":
                        response = addAlbum(props);
                        break;
                    case "add_song_to_album":
                        response = addSongToAlbum(props);
                        break;
                    case "add_studio_to_album":
                        response = addStudioToAlbum(props);
                        break;
                    case "remove_from_album":
                        response = removeAlbum(props);
                        break;
                    case "change_track_num":
                        response = changeTrackNum(props);
                        break;
                    case "add_artist":
                        response = addArtist(props);
                        break;
                    case "add_musician_to_artist":
                        response = addMusicianToArtist(props);
                        break;
                    case "add_label":
                        response = addLabel(props);
                        break;
                    case "add_studio":
                        response = addStudio(props);
                        break;
                    case "add_musician":
                        response = addMusician(props);
                        break;
                    case "update_song":
                        response = updateSong(props);
                        break;
                    case "update_album":
                        response = updateAlbum(props);
                        break;
                    case "update_label":
                        response = updateLabel(props);
                        break;
                    case "update_studio":
                        response = updateStudio(props);
                        break;
                    case "update_musician":
                        response = updateMusician(props);
                        break;
                    case "change_musician_role":
                        response = changeMusicianRole(props);
                        break;
                    case "update_artist":
                        response = updateArtist(props);
                        break;
                    case "remove_song":
                        response = removeSong(props);
                        break;
                    case "remove_composer_from_song":
                        response = removeComposerFromSong(props);
                        break;
                    case "remove_album":
                        response = removeAlbum(props);
                        break;
                    case "remove_song_from_album":
                        response = removeSongFromAlbum(props);
                        break;
                    case "remove_studio_from_album":
                        response = removeStudioFromAlbum(props);
                        break;
                    case "remove_artist":
                        response = removeArtist(props);
                        break;
                    case "remove_musician_from_artist":
                        response = removeMusicianFromArtist(props);
                        break;
                    case "song_detail":
                        response = songDetail(props);
                        break;
                    case "album_detail":
                        response = albumDetail(props);
                        break;
                    case "artist_detail":
                        response = artistDetail(props);
                        break;
                    case "label_detail":
                        response = labelDetail(props);
                        break;
                    case "studio_detail":
                        response = studioDetail(props);
                        break;
                    case "musician_detail":
                        response = musicianDetail(props);
                        break;
                    case "make_editor":
                        response = makeEditor(props);
                        break;
                    case "check_editor":
                        response = checkEditor(props);
                        break;
                    case "save_song_notif":
                        response = saveSongNotif(props);
                        break;
                    case "save_album_notif":
                        response = saveAlbumNotif(props);
                        break;
                    case "save_artist_notif":
                        response = saveArtistNotif(props);
                        break;
                    case "save_editor_notif":
                        response = saveEditorNotif(props);
                        break;
                    case "song_editors":
                        response = songEditors(props);
                        break;
                    case "album_editors":
                        response = albumEditors(props);
                        break;
                    case "artist_editors":
                        response = artistEditors(props);
                        break;
                    case "flush_notifs":
                        response = flushNotifs(props);
                        break;
                    case "song_search":
                        response = songSearch(props);
                        break;
                    case "album_search":
                        response = albumSearch(props);
                        break;
                    case "artist_search":
                        response = artistSearch(props);
                        break;
                    case "label_search":
                        response = labelSearch(props);
                        break;
                    case "studio_search":
                        response = studioSearch(props);
                        break;
                    case "musician_search":
                        response = musicianSearch(props);
                        break;
                    case "review_album":
                        response = reviewAlbum(props);
                        break;
                    case "download_url":
                        response = downloadUrl(props);
                        if (response == null) return;
                    case "upload_url":
                        response = uploadUrl(props);
                        break;
                    case "share_song":
                        response = shareSong(props);
                        if (response == null) return;
                        break;
                    case "create_playlist":
                        response = createPlaylist(props);
                        break;
                    case "rename_playlist":
                        response = renamePlaylist(props);
                        break;
                    case "add_to_playlist":
                        response = addToPlaylist(props);
                        break;
                    case "remove_from_playlist":
                        response = removeFromPlaylist(props);
                        break;
                    case "change_playlist_position":
                        response = changePlaylistPosition(props);
                        break;
                    case "remove_playlist":
                        response = removePlaylist(props);
                        break;
                    case "playlist_details":
                        response = playlistDetails(props);
                        break;
                    case "playlist_search":
                        response = playlistSearch(props);
                        break;
                    case "toggle_playlist_public":
                        response = togglePlaylistPublic(props);
                        break;
                    case "remove_label":
                        response = removeLabel(props);
                        break;
                    case "remove_studio":
                        response = removeStudio(props);
                        break;
                    case "remove_musician":
                        response = removeMusician(props);
                        break;
                    case "set_dropbox_token":
                        response = setDropBoxToken(props);
                        break;
                }
                if (props.containsKey("username"))
                    response = "username:" + props.get("username") + ";" + response;
                response = "request_id:" + props.get("request_id") + ";" + response;
                response = "type:" + props.get("action") + ";" + response;
                byte[] buffer = response.getBytes();

                try {
                    DatagramPacket
                            packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(multiCastAddress), multiCastPort);
                    synchronized (socket) {
                        socket.send(packet);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        q.close();
    }

    //<editor-fold desc="Files">

    private String shareSong(HashMap<String, String> props) {
        String status = "ok";
        String username = props.get("username");
        try {
            if (util.userExists(username)) {
                String shared_username = props.get("shared_username");
                if (util.userExists(shared_username)) {
                    String song_id = props.get("song_id");
                    if (util.songExists(song_id)) {
                        ResultSet rs = q.executeQuery(
                                "SELECT creator_username\n" +
                                        "FROM file\n" +
                                        "WHERE creator_username = '" + username + "'\n" +
                                        "AND song_id = " + song_id + "\n" +
                                        "UNION\n" +
                                        "SELECT creator_username\n" +
                                        "FROM shared_file\n" +
                                        "WHERE shared_username = '" + username + "'\n" +
                                        "AND song_id = " + song_id + ";"
                        );
                        if (rs.next()) {
                            q.executeQuery(
                                    "INSERT INTO shared_file (creator_username, song_id, shared_username) " +
                                            "VALUES ('" + rs.getString(1) + "', " + song_id + ",'" + shared_username + "');"
                            );
                        } else return null;
                    } else
                        status = "song_null";
                } else
                    status = "shared_user_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String uploadUrl(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String song_id = props.get("song_id");
                if (util.songExists(song_id)) {
                    status += ";host:" + TCPAddress + ";port:" + TCPPort;
                } else
                    status = "song_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String downloadUrl(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String song_id = props.get("song_id");
                if (util.songExists(song_id)) {
                    ResultSet rs = q.executeQuery(
                            "SELECT exists(SELECT 1\n" +
                                    "FROM file\n" +
                                    "WHERE creator_username = '" + username + "'\n" +
                                    "AND song_id = " + song_id + "\n" +
                                    "UNION\n" +
                                    "SELECT 1\n" +
                                    "FROM shared_file\n" +
                                    "WHERE shared_username = '" + username + "'\n" +
                                    "AND song_id = " + song_id + ");"
                    );
                    rs.next();
                    if (!rs.getBoolean(1))
                        return null;
                    else status += ";host:" + multiCastAddress + ";port:" + TCPPort;
                } else
                    status = "song_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    //</editor-fold>

    private String reviewAlbum(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String album_id = props.get("album_id");
                if (util.albumExists(album_id)) {
                    if (!util.hasReviewed(username, album_id)) {
                        String score = props.get("score");
                        String detail = props.get("detail");
                        q.executeQuery(
                                "INSERT INTO review(score, detail, reviewer_username, album_id, artist_id) " +
                                        "VALUES (" + score + ", '" + detail + "', '" + username + "', '" + album_id + "', " +
                                        "(SELECT artist_id FROM album WHERE album_id = " + album_id + ")");
                    } else
                        status = "already_exists";
                } else
                    status = "album_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;

    }

    //<editor-fold desc="Playlists">

    private String addToPlaylist(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String playlist_id = props.get("playlist_id");
                if (util.playlistExists(username, playlist_id)) {
                    String song_id = props.get("song_id");
                    if (!util.existsInPlaylist(username, playlist_id, song_id)) {
                        q.executeQuery(
                                "INSERT INTO playlist_song(owner_username, playlist_id, song_id, position)\n" +
                                        "VALUES ('" + username + "', " + playlist_id + ", " + song_id + "," +
                                        "(SELECT coalesce(max(position) + 1, 1) FROM playlist_song WHERE playlist_id = " + playlist_id + ")));"
                        );
                    } else
                        status = "already_exists";
                } else
                    status = "playlist_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String changePlaylistPosition(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String playlist_id = props.get("playlist_id");
                if (util.playlistExists(username, playlist_id)) {
                    String song_id = props.get("song_id");
                    if (!util.existsInPlaylist(username, playlist_id, song_id)) {
                        ResultSet rs_num = q.executeQuery(
                                "SELECT position\n" +
                                        "FROM playlist_song\n" +
                                        "WHERE playlist_id = " + playlist_id + " AND song_id = " + song_id + ";"
                        );
                        rs_num.next();
                        String position = rs_num.getString(1);
                        String new_position = props.get("position");
                        if (Integer.valueOf(new_position) > Integer.valueOf(position)) {
                            q.executeQuery(
                                    "UPDATE playlist_song\n" +
                                            "SET position = position - 1\n" +
                                            "WHERE playlist_id = " + playlist_id + "\n" +
                                            "AND position > " + position + "\n" +
                                            "AND position <= " + new_position + ";"
                            );
                        } else if (Integer.valueOf(new_position) < Integer.valueOf(position)) {
                            q.executeQuery(
                                    "UPDATE playlist_song\n" +
                                            "SET position = position + 1\n" +
                                            "WHERE playlist_id = " + playlist_id + "\n" +
                                            "AND position < " + position + "\n" +
                                            "AND position >= " + new_position + ";"
                            );
                        }
                        q.executeQuery(
                                "UPDATE playlist_song\n" +
                                        "SET position = " + new_position + "\n" +
                                        "WHERE playlist_id = " + playlist_id + "\n" +
                                        "AND song_id = " + song_id + ";"
                        );
                    } else
                        status = "doesnt_exist";
                } else
                    status = "playlist_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String removeFromPlaylist(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String playlist_id = props.get("playlist_id");
                if (util.playlistExists(username, playlist_id)) {
                    String song_id = props.get("song_id");
                    if (util.existsInPlaylist(username, playlist_id, song_id)) {
                        q.executeQuery(
                                "UPDATE playlist_song\n" +
                                        "SET position = position - 1\n" +
                                        "WHERE playlist_id = " + playlist_id + "\n" +
                                        "AND position > (\n" +
                                        "    SELECT position\n" +
                                        "    FROM playlist_song\n" +
                                        "    WHERE song_id = " + song_id + "\n" +
                                        "    AND playlist_id = " + playlist_id + "\n" +
                                        ")"
                        );
                        q.executeQuery(
                                "DELETE FROM playlist_song\n" +
                                        "WHERE playlist_id = " + playlist_id + "\n" +
                                        "AND owner_username = '" + username + "'\n" +
                                        "and song_id = " + song_id + ";"
                        );
                    } else
                        status = "doesnt_exist";
                } else
                    status = "playlist_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String createPlaylist(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String name = props.get("name");
                if (!util.playlistExists(username, name)) {
                    q.executeQuery(
                            "INSERT INTO playlist(name, owner_username) \n" +
                                    "VALUES ('" + name + "', '" + username + "',);"
                    );
                } else
                    status = "already_exists";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String renamePlaylist(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String playlist_id = props.get("playlist_id");
                if (util.playlistExists(username, playlist_id)) {
                    q.executeQuery(
                            "UPDATE playlist " +
                                    "SET name = '" + props.get("name") + "' " +
                                    "WHERE playlist_id = " + playlist_id + ";");
                } else
                    status = "playlist_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String removePlaylist(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String playlist_id = props.get("playlist_id");
                if (util.playlistExists(username, playlist_id)) {
                    q.executeQuery(
                            "DELETE FROM playlist_song\n" +
                                    "WHERE playlist_id = " + playlist_id + ";"
                    );
                    q.executeQuery(
                            "DELETE FROM playlist\n" +
                                    "WHERE playlist_id = " + playlist_id + ";"
                    );
                } else
                    status = "playlist_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String playlistDetails(HashMap<String, String> props) {
        String status = "ok";
        String data_info = "";
        String data_songs = "";
        try {
            String username = props.get("username");
            String playlist_id = props.get("playlist_id");
            if (util.playlistExists(username, playlist_id)) {
                ResultSet rs_info = q.executeQuery(
                        "SELECT name, is_public, owner_username FROM playlist WHERE playlist_id = " + playlist_id + ";"
                );
                ResultSetMetaData rsmd_info = rs_info.getMetaData();
                ResultSet rs_songs = q.executeQuery(
                        "SELECT pl.song_id, so.name, pl.position\n" +
                                "FROM song so, playlist_song pl\n" +
                                "WHERE so.song_id in (\n" +
                                "    SELECT song_id\n" +
                                "    FROM playlist_song\n" +
                                "    WHERE playlist_id = " + playlist_id + "\n" +
                                ")\n" +
                                "AND so.song_id = pl.song_id\n" +
                                "ORDER BY pl.position;"
                );
                rs_info.next();
                for (int i = 1; i <= rsmd_info.getColumnCount(); i++) {
                    data_info += rsmd_info.getColumnLabel(i) + ":" + rs_info.getObject(i) + ";";
                }
                while (rs_songs.next()) {
                    data_songs += "<song_id»" + rs_songs.getString("song_id") +
                            "~name»" + rs_songs.getString("name") +
                            "~track_num»" + rs_songs.getInt("track_num") + ">";
                }
                if (data_songs.equals(""))
                    data_songs = "songs:/;";
                else
                    data_songs = "songs:" + data_songs + ";";
            } else
                status = "playlist_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return data_info + data_songs + "status:" + status;
    }

    private String playlistSearch(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String query = props.get("query");
                ResultSet rs = q.executeQuery(
                        "SELECT playlist_id, name\n" +
                                "FROM playlist\n" +
                                "WHERE name LIKE '%" + query + "%'\n" +
                                "AND (owner_username = '" + username + "' OR is_public = TRUE)"
                );
                String playlists = "";
                while (rs.next())
                    playlists += "<playlist_id»" + rs.getInt(1) + "~name»" + rs.getString(2) + ">";
                if (playlists.equals(""))
                    playlists = "/";
                status += ";playlists:" + playlists;
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String togglePlaylistPublic(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String playlist_id = props.get("playlist_id");
                if (util.playlistExists(username, playlist_id)) {
                    q.executeQuery(
                            "UPDATE playlist " +
                                    "SET is_public = NOT is_public\n" +
                                    "WHERE playlist_id = " + playlist_id + ";");
                } else
                    status = "playlist_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    //</editor-fold>

    //<editor-fold desc="Search">

    private String songSearch(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String query = props.get("query");
                if (query.equals("/")) query = "";
                ResultSet rs = q.executeQuery(
                        "SELECT song_id, name\n" +
                                "FROM song\n" +
                                "WHERE name LIKE '%" + query + "%'\n" +
                                "OR genre LIKE '%" + query + "%'\n" +
                                "OR lyric_writer_id IN (\n" +
                                "    SELECT artist_id\n" +
                                "    FROM artist\n" +
                                "    WHERE name LIKE '%" + query + "%'\n" +
                                ")\n" +
                                "OR song_id IN (\n" +
                                "    SELECT song_id\n" +
                                "    FROM song_album\n" +
                                "    WHERE album_id IN (\n" +
                                "        SELECT album_id\n" +
                                "        FROM album\n" +
                                "        WHERE name LIKE '%" + query + "%'\n" +
                                "        OR artist_id IN (\n" +
                                "            SELECT artist_id\n" +
                                "            FROM artist\n" +
                                "            WHERE name LIKE '%" + query + "%'\n" +
                                "        )\n" +
                                "    )\n" +
                                ")"
                );
                String songs = "";
                while (rs.next())
                    songs += "<song_id»" + rs.getInt(1) + "~name»" + rs.getString(2) + ">";
                if (songs.equals(""))
                    songs = "/";
                status += ";songs:" + songs;
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String albumSearch(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String query = props.get("query");
                if (query.equals("/")) query = "";
                ResultSet rs = q.executeQuery(
                        "SELECT album_id, name\n" +
                                "FROM album\n" +
                                "WHERE name LIKE '%" + query + "%'\n" +
                                "OR album.artist_id IN (\n" +
                                "    SELECT artist_id\n" +
                                "    FROM artist\n" +
                                "    WHERE name LIKE '%" + query + "%'\n" +
                                ")\n" +
                                "OR label_id IN (\n" +
                                "    SELECT label_id\n" +
                                "    FROM label\n" +
                                "    WHERE name LIKE '%" + query + "%'\n" +
                                ")\n" +
                                "OR album_id IN (\n" +
                                "    SELECT album_id\n" +
                                "    FROM song_album\n" +
                                "    WHERE song_id IN (\n" +
                                "        SELECT song_id\n" +
                                "        FROM song\n" +
                                "        WHERE name LIKE '%" + query + "%'\n" +
                                "    )\n" +
                                "    UNION\n" +
                                "    SELECT album_id\n" +
                                "    FROM album_studio\n" +
                                "    WHERE studio_id IN (\n" +
                                "        SELECT studio_id\n" +
                                "        FROM studio\n" +
                                "        WHERE name LIKE '%" + query + "%'\n" +
                                "    )\n" +
                                ")"
                );
                String albums = "";
                while (rs.next())
                    albums += "<album_id»" + rs.getInt(1) + "~name»" + rs.getString(2) + ">";
                if (albums.equals(""))
                    albums = "/";
                status += ";albums:" + albums;
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String artistSearch(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String query = props.get("query");
                if (query.equals("/")) query = "";
                ResultSet rs = q.executeQuery(
                        "SELECT artist_id, name\n" +
                                "FROM artist\n" +
                                "WHERE name LIKE '%" + query + "%'\n" +
                                "OR artist_id IN (\n" +
                                "    SELECT artist_id\n" +
                                "    FROM album\n" +
                                "    WHERE name LIKE '%" + query + "%'\n" +
                                "    OR album_id IN (\n" +
                                "        SELECT album_id\n" +
                                "        FROM song_album\n" +
                                "        WHERE song_id IN (\n" +
                                "            SELECT song_id\n" +
                                "            FROM song\n" +
                                "            WHERE name LIKE '%" + query + "%'\n" +
                                "        )\n" +
                                "        UNION\n" +
                                "        SELECT album_id\n" +
                                "        FROM album_studio\n" +
                                "        WHERE name LIKE '%" + query + "%'\n" +
                                "        OR studio_id IN (\n" +
                                "            SELECT studio_id\n" +
                                "            FROM studio\n" +
                                "            WHERE name LIKE '%" + query + "%'\n" +
                                "        )\n" +
                                "    )\n" +
                                "    OR label_id IN (\n" +
                                "        SELECT label_id\n" +
                                "        FROM label\n" +
                                "        WHERE name LIKE '%" + query + "%'\n" +
                                "    )\n" +
                                ")"
                );
                String artists = "";
                while (rs.next())
                    artists += "<artist_id»" + rs.getInt(1) + "~name»" + rs.getString(2) + ">";
                if (artists.equals(""))
                    artists = "/";
                status += ";artists:" + artists;
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String labelSearch(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String query = props.get("query");
                if (query.equals("/")) query = "";
                ResultSet rs = q.executeQuery(
                        "SELECT label_id, name\n" +
                                "FROM label\n" +
                                "WHERE name LIKE '%" + query + "%';"
                );
                String labels = "";
                while (rs.next())
                    labels += "<label_id»" + rs.getInt(1) + "~name»" + rs.getString(2) + ">";
                if (labels.equals(""))
                    labels = "/";
                status += ";labels:" + labels;
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String studioSearch(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String query = props.get("query");
                if (query.equals("/")) query = "";
                ResultSet rs = q.executeQuery(
                        "SELECT studio_id, name\n" +
                                "FROM studio\n" +
                                "WHERE name LIKE '%" + query + "%';"
                );
                String studios = "";
                while (rs.next())
                    studios += "<studio_id»" + rs.getInt(1) + "~name»" + rs.getString(2) + ">";
                if (studios.equals(""))
                    studios = "/";
                status += ";studios:" + studios;
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String musicianSearch(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String query = props.get("query");
                if (query.equals("/")) query = "";
                ResultSet rs = q.executeQuery(
                        "SELECT musician_id, name\n" +
                                "FROM musician\n" +
                                "WHERE name LIKE '%" + query + "%';"
                );
                String musicians = "";
                while (rs.next())
                    musicians += "<musician_id»" + rs.getInt(1) + "~name»" + rs.getString(2) + ">";
                if (musicians.equals(""))
                    musicians = "/";
                status += ";musicians:" + musicians;
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    //</editor-fold>

    private String flushNotifs(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                ResultSet rs_song_notifs = q.executeQuery(
                        "SELECT n.song_id, s.name, n.editor_username\n" +
                                "FROM song_notification n, (\n" +
                                "  SELECT song_id, name\n" +
                                "  FROM song\n" +
                                ") s\n" +
                                "WHERE receiver_username = '" + username + "'\n" +
                                "AND n.song_id = s.song_id;"
                );
                String notifs = "";
                while (rs_song_notifs.next()) {
                    notifs += "<type»song~song_id»" + rs_song_notifs.getInt(1) +
                            "~name»" + rs_song_notifs.getString(2) +
                            "~editor»" + rs_song_notifs.getString(3) + ">";
                }
                ResultSet rs_album_notifs = q.executeQuery(
                        "SELECT al.name, aln.album_id, ar.name artist_name, aln.artist_id, aln.editor_username\n" +
                                "FROM album_notification aln, album al, artist ar\n" +
                                "WHERE receiver_username = '" + username + "'\n" +
                                "AND aln.artist_id = ar.artist_id " +
                                "AND aln.album_id = al.album_id;"
                );
                while (rs_album_notifs.next()) {
                    notifs += "<type»album~album_name»" + rs_album_notifs.getInt(1) +
                            "~artist_name»" + rs_album_notifs.getString(2) +
                            "~artist_id»" + rs_album_notifs.getString(3) +
                            "~editor»" + rs_album_notifs.getString(4) + ">";
                }
                ResultSet rs_artist_notifs = q.executeQuery(
                        "SELECT n.artist_id, a.name, n.editor_username\n" +
                                "FROM artist_notification n, (\n" +
                                "  SELECT artist_id, name\n" +
                                "  FROM artist\n" +
                                ") a\n" +
                                "WHERE receiver_username = '" + username + "'\n" +
                                "AND n.artist_id = a.artist_id;"
                );
                while (rs_artist_notifs.next()) {
                    notifs += "<type»artist~artist_id»" + rs_artist_notifs.getInt(1) +
                            "~name»" + rs_artist_notifs.getString(2) +
                            "~editor»" + rs_artist_notifs.getString(3) + ">";
                }
                ResultSet rs_editor = q.executeQuery(
                        "SELECT editor_username " +
                                "FROM editor_notification " +
                                "WHERE receiver_username = '" + username + "';"
                );
                if (rs_editor.next()) {
                    notifs += "<type»editor~editor_username»" + rs_editor.getString(1) + ">";
                }
                if (notifs.equals(""))
                    notifs = "/";
                status += ";notifs:" + notifs;
                q.executeQuery("DELETE FROM song_notification WHERE receiver_username = '" + username + "';");
                q.executeQuery("DELETE FROM album_notification WHERE receiver_username = '" + username + "';");
                q.executeQuery("DELETE FROM artist_notification WHERE receiver_username = '" + username + "';");
                q.executeQuery("DELETE FROM editor_notification WHERE receiver_username = '" + username + "';");
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    //<editor-fold desc="Editors">

    private String artistEditors(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String artist_id = props.get("artist_id");
                    if (util.artistExists(artist_id)) {
                        ResultSet rs = q.executeQuery(
                                "SELECT editor_username\n" +
                                        "FROM artist_editor\n" +
                                        "WHERE artist_id = " + artist_id + ";"
                        );
                        status += ";editors:";
                        while (rs.next()) {
                            status += rs.getString(1) + ",";
                        }
                        status = status.substring(0, status.length() - 1);
                    } else
                        status = "artist_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String albumEditors(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String album_id = props.get("album_id");
                    if (util.albumExists(album_id)) {
                        ResultSet rs = q.executeQuery(
                                "SELECT editor_username\n" +
                                        "FROM album_editor\n" +
                                        "WHERE album_id = " + album_id + "\n;"
                        );
                        status += ";editors:";
                        while (rs.next()) {
                            status += rs.getString(1) + ",";
                        }
                        status = status.substring(0, status.length() - 1);
                    } else
                        status = "album_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String songEditors(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String song_id = props.get("song_id");
                    if (util.songExists(song_id)) {
                        ResultSet rs = q.executeQuery(
                                "SELECT editor_username\n" +
                                        "FROM song_editor\n" +
                                        "WHERE song_id = " + song_id + ";"
                        );
                        status += ";editors:";
                        while (rs.next()) {
                            status += rs.getString(1) + ",";
                        }
                        status = status.substring(0, status.length() - 1);
                    } else
                        status = "song_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    //</editor-fold>

    //<editor-fold desc="Save Notifs">

    private String saveEditorNotif(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String receiver_user = props.get("receiver_username");
                    if (util.userExists(receiver_user)) {
                        q.executeQuery(
                                "INSERT INTO editor_notification(editor_username,receiver_username) " +
                                        "VALUES ('" + username + "', '" + receiver_user + "');"
                        );
                    } else
                        status = "receiver_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String saveArtistNotif(HashMap<String, String> props) {
        String status;
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String artist_id = props.get("artist_id");
                    if (util.artistExists(artist_id)) {
                        String[] notif_receivers = props.get("notif_receivers").split(",");
                        ArrayList<String> null_receivers = new ArrayList<>();
                        for (String receiver : notif_receivers) {
                            if (!util.userExists(receiver))
                                null_receivers.add(receiver);
                        }
                        if (null_receivers.size() == 0) {
                            String query = "INSERT INTO artist_notification (artist_id, receiver_username, editor_username) " +
                                    "VALUES ";
                            for (String receiver : notif_receivers) {
                                query += "(" + artist_id + ", '" + receiver + "', '" + username + "'), ";
                            }
                            query = query.substring(0, query.length() - 2) + " ON CONFLICT DO NOTHING;";
                            q.executeQuery(query);
                            status = "ok";
                        } else {
                            status = "receivers_null;receivers:";
                            for (String receiver : null_receivers) {
                                status += receiver + ",";
                            }
                            status = status.substring(0, status.length() - 1);
                        }
                    } else
                        status = "artist_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String saveAlbumNotif(HashMap<String, String> props) {
        String status;
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String album_id = props.get("album_id");
                    if (util.albumExists(album_id)) {
                        String[] notif_receivers = props.get("notif_receivers").split(",");
                        ArrayList<String> null_receivers = new ArrayList<>();
                        for (String receiver : notif_receivers) {
                            if (!util.userExists(receiver))
                                null_receivers.add(receiver);
                        }
                        if (null_receivers.size() == 0) {
                            String query = "INSERT INTO album_notification (artist_id, album_id, receiver_username, editor_username) " +
                                    "VALUES ";
                            for (String receiver : notif_receivers) {
                                query += "((SELECT artist_id FROM album WHERE album_id = " + album_id + "),'" + album_id + "', '" + receiver + "', '" + username + "'), ";
                            }
                            query = query.substring(0, query.length() - 2) + " ON CONFLICT DO NOTHING;";
                            q.executeQuery(query);
                            status = "ok";
                        } else {
                            status = "receivers_null;receivers:";
                            for (String receiver : null_receivers) {
                                status += receiver + ",";
                            }
                            status = status.substring(0, status.length() - 1);
                        }
                    } else
                        status = "album_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String saveSongNotif(HashMap<String, String> props) {
        String status;
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String song_id = props.get("song_id");
                    if (util.songExists(song_id)) {
                        String[] notif_receivers = props.get("notif_receivers").split(",");
                        ArrayList<String> null_receivers = new ArrayList<>();
                        for (String receiver : notif_receivers) {
                            if (!util.userExists(receiver))
                                null_receivers.add(receiver);
                        }
                        if (null_receivers.size() == 0) {
                            String query = "INSERT INTO song_notification (song_id, receiver_username, editor_username) " +
                                    "VALUES ";
                            for (String receiver : notif_receivers) {
                                query += "(" + song_id + ", '" + receiver + "', '" + username + "'), ";
                            }
                            query = query.substring(0, query.length() - 2) + " ON CONFLICT DO NOTHING;";
                            q.executeQuery(query);
                            status = "ok";
                        } else {
                            status = "receivers_null;receivers:";
                            for (String receiver : null_receivers) {
                                status += receiver + ",";
                            }
                            status = status.substring(0, status.length() - 1);
                        }
                    } else
                        status = "song_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    //</editor-fold>

    private String checkEditor(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (!util.isEditor(username))
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String makeEditor(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String editor_username = props.get("editor_username");
                    if (util.userExists(editor_username)) {
                        if (!util.isEditor(editor_username)) {
                            q.executeQuery(
                                    "UPDATE app_user " +
                                            "SET is_editor = TRUE " +
                                            "WHERE username = '" + editor_username + "';"
                            );
                        } else
                            status = "is_editor";
                    } else
                        status = "editor_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    //<editor-fold desc="Detail">

    private String artistDetail(HashMap<String, String> props) {
        String status = "ok";
        String data_info = "";
        String data_albums = "";
        String data_members = "";
        try {
            String artist_id = props.get("artist_id");
            if (util.artistExists(artist_id)) {
                ResultSet rs_info = q.executeQuery(
                        "SELECT artist_id, name, description\n" +
                                "FROM artist " +
                                "WHERE artist_id = " + artist_id + ";"
                );
                ResultSetMetaData rsmd_info = rs_info.getMetaData();
                ResultSet rs_albums = q.executeQuery(
                        "SELECT name, album_id\n" +
                                "FROM album\n" +
                                "WHERE artist_id = " + artist_id + ";"
                );
                ResultSet rs_members = q.executeQuery(
                        "SELECT ro.musician_id, name\n" +
                                "FROM musician_role ro, musician mu\n" +
                                "WHERE ro.musician_id = mu.musician_id\n" +
                                "AND ro.artist_id = " + artist_id + ";"
                );
                rs_info.next();
                for (int i = 1; i <= rsmd_info.getColumnCount(); i++) {
                    data_info += rsmd_info.getColumnLabel(i) + ":" + rs_info.getObject(i) + ";";
                }

                while (rs_albums.next()) {
                    data_albums += "<album_id»" + rs_albums.getString("album_id") +
                            "~name»" + rs_albums.getString("name") + ">";
                }
                if (data_albums.equals(""))
                    data_albums = "albums:/;";
                else
                    data_albums = "albums:" + data_albums + ";";

                while (rs_members.next()) {
                    data_members += "<musician_id»" + rs_members.getString("musician_id") +
                            "~name»" + rs_members.getString("name") + ">";
                }
                if (data_members.equals(""))
                    data_members = "members:/;";
                else
                    data_members = "members:" + data_members + ";";
            } else
                status = "artist_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return data_info + data_albums + data_members + "status:" + status;
    }

    private String albumDetail(HashMap<String, String> props) {
        String status = "ok";
        String data_info = "";
        String data_songs = "";
        String data_studios = "";
        String data_reviews = "";
        String data_score = "";
        try {
            String album_id = props.get("album_id");
            if (util.albumExists(album_id)) {
                ResultSet rs_info = q.executeQuery(
                        "SELECT al.name album_name, al.album_id, al.description, la.label_id, la.name label_name, ar.artist_id, ar.name artist_name\n" +
                                "FROM artist ar, album al LEFT JOIN label la ON (al.label_id = la.label_id OR al.label_id is null)\n" +
                                "WHERE al.album_id = " + album_id + "\n" +
                                "AND ar.artist_id = al.artist_id"
                );
                ResultSetMetaData rsmd_info = rs_info.getMetaData();
                ResultSet rs_songs = q.executeQuery(
                        "SELECT sa.song_id, so.name, sa.track_num\n" +
                                "FROM song so, song_album sa\n" +
                                "WHERE so.song_id in (\n" +
                                "    SELECT song_id\n" +
                                "    FROM song_album\n" +
                                "    WHERE album_id = " + album_id + "\n" +
                                ")\n" +
                                "AND so.song_id = sa.song_id\n" +
                                "AND sa.album_id = " + album_id + ";"
                );
                ResultSet rs_studios = q.executeQuery(
                        "SELECT st.studio_id, name \n" +
                                "FROM album_studio al, studio st\n" +
                                "WHERE album_id = " + album_id + "\n" +
                                "AND al.studio_id = st.studio_id;"
                );
                ResultSet rs_reviews = q.executeQuery(
                        "SELECT reviewer_username, score, detail\n" +
                                "FROM review\n" +
                                "WHERE album_id = " + album_id + "\n;"
                );
                ResultSet rs_avg_score = q.executeQuery(
                        "SELECT avg(score)\n" +
                                "FROM review\n" +
                                "WHERE album_id = " + album_id + "\n;"
                );
                rs_info.next();
                for (int i = 1; i <= rsmd_info.getColumnCount(); i++) {
                    data_info += rsmd_info.getColumnLabel(i) + ":" + rs_info.getObject(i) + ";";
                }
                while (rs_songs.next()) {
                    data_songs += "<song_id»" + rs_songs.getString("song_id") +
                            "~name»" + rs_songs.getString("name") +
                            "~track_num»" + rs_songs.getInt("track_num") + ">";
                }
                if (data_songs.equals(""))
                    data_songs = "songs:/;";
                else
                    data_songs = "songs:" + data_songs + ";";

                while (rs_studios.next()) {
                    data_studios += "<studio_id»" + rs_studios.getString("studio_id") +
                            "~name»" + rs_studios.getString("name") + ">";
                }
                if (data_studios.equals(""))
                    data_studios = "studios:/;";
                else
                    data_studios = "studios:" + data_studios + ";";

                while (rs_reviews.next()) {
                    data_reviews += "<username»" + rs_reviews.getString(1) +
                            "~score»" + rs_reviews.getInt(2) +
                            "~detail»" + rs_reviews.getString(3) + ">";
                }
                if (data_reviews.equals(""))
                    data_reviews = "reviews:/;";
                else
                    data_reviews = "reviews:" + data_reviews + ";";

                rs_avg_score.next();
                float score = rs_avg_score.getFloat(1);
                if (rs_avg_score.wasNull())
                    score = 5;
                data_score = "score:" + score + ";";
            } else
                status = "album_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return data_info + data_songs + data_reviews + data_score + data_studios + "status:" + status;
    }

    private String songDetail(HashMap<String, String> props) {
        String status = "ok";
        String data_info = "";
        String data_albums = "";
        String data_composers = "";
        try {
            String song_id = props.get("song_id");
            if (util.songExists(song_id)) {
                ResultSet rs_info = q.executeQuery(
                        "SELECT so.song_id, so.name, genre, length, lyrics, lyric_writer_id, mu.name lyric_writer\n" +
                                "FROM song so LEFT JOIN musician mu ON (so.lyric_writer_id = mu.musician_id OR so.lyric_writer_id is null)\n" +
                                "WHERE song_id = " + song_id + ";"
                );
                ResultSetMetaData rsmd_info = rs_info.getMetaData();
                ResultSet rs_albums = q.executeQuery(
                        "SELECT al.name album_name, sal.album_id, ar.name, sal.artist_id\n" +
                                "FROM album al, song_album sal, artist ar\n" +
                                "WHERE sal.song_id = " + song_id + "\n" +
                                "AND sal.album_id = al.album_id\n" +
                                "AND sal.artist_id = ar.artist_id;"
                );
                ResultSet rs_composers = q.executeQuery(
                        "SELECT mu.musician_id, name\n" +
                                "FROM musician mu, song_composer sc\n" +
                                "WHERE mu.musician_id = sc.musician_id\n" +
                                "AND song_id = " + song_id + ";"
                );
                rs_info.next();
                for (int i = 1; i <= rsmd_info.getColumnCount(); i++) {
                    data_info += rsmd_info.getColumnLabel(i) + ":" + rs_info.getObject(i) + ";";
                }
                while (rs_albums.next()) {
                    data_albums += "<album_name»" + rs_albums.getString(1) +
                            "~album_id»" + rs_albums.getString(2) +
                            "~artist_name»" + rs_albums.getString(3) +
                            "~artist_id»" + rs_albums.getString(4) + ">";
                }
                if (data_albums.equals(""))
                    data_albums = "albums:/;";
                else
                    data_albums = "albums:" + data_albums + ";";
                while (rs_composers.next()) {
                    data_composers += "<composer_name»" + rs_composers.getString(1) +
                            "~composer_id»" + rs_composers.getString(2) + ">";
                }
                if (data_composers.equals(""))
                    data_composers = "composers:/;";
                else
                    data_composers = "composers:" + data_composers + ";";
            } else
                status = "song_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return data_info + data_albums + data_composers + "status:" + status;
    }

    private String labelDetail(HashMap<String, String> props) {
        String data_info = "";
        String status = "ok";
        try {
            String label_id = props.get("label_id");
            if (util.labelExists(label_id)) {
                ResultSet rs_info = q.executeQuery(
                        "SELECT label_id, name, description\n" +
                                "FROM label " +
                                "WHERE label_id = " + label_id + ";"
                );
                ResultSetMetaData rsmd_info = rs_info.getMetaData();
                rs_info.next();
                for (int i = 1; i <= rsmd_info.getColumnCount(); i++) {
                    data_info += rsmd_info.getColumnLabel(i) + ":" + rs_info.getObject(i) + ";";
                }
            } else
                status = "label_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return data_info + "status:" + status;
    }

    private String studioDetail(HashMap<String, String> props) {
        String data_info = "";
        String status = "ok";
        try {
            String studio_id = props.get("studio_id");
            if (util.studioExists(studio_id)) {
                ResultSet rs_info = q.executeQuery(
                        "SELECT studio_id, name, description\n" +
                                "FROM studio " +
                                "WHERE studio_id = " + studio_id + ";"
                );
                ResultSetMetaData rsmd_info = rs_info.getMetaData();
                rs_info.next();
                for (int i = 1; i <= rsmd_info.getColumnCount(); i++) {
                    data_info += rsmd_info.getColumnLabel(i) + ":" + rs_info.getObject(i) + ";";
                }
            } else
                status = "studio_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return data_info + "status:" + status;
    }

    private String musicianDetail(HashMap<String, String> props) {
        String data_info = "";
        String status = "ok";
        try {
            String musician_id = props.get("musician_id");
            if (util.musicianExists(musician_id)) {
                ResultSet rs_info = q.executeQuery(
                        "SELECT musician_id, name, description\n" +
                                "FROM musician " +
                                "WHERE musician_id = " + musician_id + ";"
                );
                ResultSetMetaData rsmd_info = rs_info.getMetaData();
                rs_info.next();
                for (int i = 1; i <= rsmd_info.getColumnCount(); i++) {
                    data_info += rsmd_info.getColumnLabel(i) + ":" + rs_info.getObject(i) + ";";
                }
            } else
                status = "musician_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return data_info + "status:" + status;
    }

    //</editor-fold>

    //<editor-fold desc="Remove">

    // TODO: 28-11-2018 check remove, tracknum not updated when remove song

    private String removeArtist(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String artist_id = props.get("artist_id");
                    if (util.artistExists(artist_id)) {

                        //<editor-fold desc="Album">

                        q.executeQuery("DELETE FROM album_studio\n" +
                                "WHERE artist_id = " + artist_id + ";");
                        q.executeQuery("DELETE FROM review\n" +
                                "WHERE artist_id = " + artist_id + ";");
                        q.executeQuery("DELETE FROM album_notification\n" +
                                "WHERE artist_id = " + artist_id + ";");
                        q.executeQuery("DELETE FROM album_editor\n" +
                                "WHERE artist_id = " + artist_id + ";");
                        q.executeQuery("DELETE FROM song_album\n" +
                                "WHERE artist_id = " + artist_id + ";");
                        q.executeQuery("DELETE FROM album WHERE artist_id = " + artist_id + ";");

                        //</editor-fold>

                        //<editor-fold desc="Artist">
                        q.executeQuery("DELETE FROM artist_editor WHERE artist_id = " + artist_id + ";");
                        q.executeQuery("DELETE FROM artist_notification WHERE artist_id = " + artist_id + ";");
                        q.executeQuery("DELETE FROM artist WHERE artist_id = " + artist_id + ";");
                        //</editor-fold>

                    } else
                        status = "artist_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String removeMusicianFromArtist(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String artist_id = props.get("artist_id");
                if (util.artistExists(artist_id)) {
                    String musician_id = props.get("musician_id");
                    if (util.musicianExists(musician_id)) {
                        if (util.musicianIsInArtist(musician_id, artist_id)) {
                            q.executeQuery(
                                    "DELETE FROM musician_role\n" +
                                            "WHERE artist_id = " + artist_id + "\n" +
                                            "AND musician_id = " + musician_id + ";"
                            );
                        } else
                            status = "doesnt_exist";
                    } else
                        status = "musician_null";
                } else
                    status = "artist_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String removeAlbum(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String album_id = props.get("album_id");
                    if (util.albumExists(album_id)) {

                        //<editor-fold desc="Song">
                        q.executeQuery(
                                "DELETE FROM song_album\n" +
                                        "WHERE album_id = " + album_id + ";"
                        );
                        //</editor-fold>

                        //<editor-fold desc="Album">
                        q.executeQuery("DELETE FROM album_studio\n" +
                                "WHERE album_id = " + album_id + ";");
                        q.executeQuery("DELETE FROM review\n" +
                                "WHERE album_id = " + album_id + ";");
                        q.executeQuery("DELETE FROM album_notification\n" +
                                "WHERE album_id = " + album_id + ";");
                        q.executeQuery("DELETE FROM album_editor\n" +
                                "WHERE album_id = " + album_id + ";");
                        q.executeQuery("DELETE FROM album\n" +
                                "WHERE album_id = " + album_id + ";");
                        //</editor-fold>

                    } else
                        status = "album_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String removeStudioFromAlbum(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String album_id = props.get("album_id");
                if (util.albumExists(album_id)) {
                    String studio_id = props.get("studio_id");
                    if (util.studioExists(studio_id)) {
                        if (util.wasRecordedIn(studio_id, album_id)) {
                            q.executeQuery(
                                    "DELETE FROM album_studio\n" +
                                            "WHERE album_id = " + album_id + "\n" +
                                            "AND studio_id = " + studio_id + ";"
                            );
                        } else
                            status = "doesnt_exist";
                    } else
                        status = "studio_null";
                } else
                    status = "album_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String removeSongFromAlbum(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String album_id = props.get("album_id");
                    if (util.albumExists(album_id)) {
                        String song_id = props.get("song_id");
                        if (util.songExists(song_id)) {
                            if (util.existsInAlbum(album_id, song_id)) {
                                q.executeQuery(
                                        "UPDATE song_album\n" +
                                                "SET track_num = track_num - 1\n" +
                                                "WHERE album_id = " + album_id + "\n" +
                                                "AND track_num > (\n" +
                                                "    SELECT track_num\n" +
                                                "    FROM song_album\n" +
                                                "    WHERE song_id = " + song_id + "\n" +
                                                "    AND album_id = " + album_id + "\n" +
                                                ")"
                                );
                                q.executeQuery(
                                        "DELETE FROM song_album\n" +
                                                "WHERE album_id = " + album_id + " AND song_id = " + song_id + ";"
                                );
                                q.executeQuery(
                                        "INSERT INTO album_editor(artist_id, album_id, editor_username) " +
                                                "VALUES (" + props.get("artist_id") + ", " + props.get("album_id") + ", '" + username + "') ON CONFLICT DO NOTHING "
                                );
                            } else
                                status = "doesnt_exist";
                        } else
                            status = "song_null";
                    } else
                        status = "album_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String removeSong(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String song_id = props.get("song_id");
                    if (util.songExists(song_id)) {
                        q.executeQuery("DELETE FROM playlist_song WHERE song_id = " + song_id + ";");
                        q.executeQuery("DELETE FROM song_notification WHERE song_id = " + song_id + ";");
                        q.executeQuery("DELETE FROM song_editor WHERE song_id = " + song_id + ";");
                        q.executeQuery("DELETE FROM song_album WHERE song_id = " + song_id + ";");
                        q.executeQuery("DELETE FROM song WHERE song_id = " + song_id + ";");
                    } else
                        status = "song_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String removeComposerFromSong(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                String song_id = props.get("song_id");
                if (util.songExists(song_id)) {
                    String composer_id = props.get("composer_id");
                    if (util.musicianExists(composer_id)) {
                        if (util.isComposer(composer_id, song_id)) {
                            q.executeQuery(
                                    "DELETE FROM song_composer\n" +
                                            "WHERE song_id = " + song_id + "\n" +
                                            "AND musician_id = " + composer_id + ";"
                            );
                        } else
                            status = "doesnt_exist";
                    } else
                        status = "composer_null";
                } else
                    status = "song_null";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String removeLabel(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String label_id = props.get("label_id");
                    if (util.labelExists(label_id)) {
                        q.executeQuery("UPDATE album SET label_id = NULL WHERE label_id = " + label_id + ";");
                        q.executeQuery("DELETE FROM label WHERE label_id = " + label_id + ";");
                    } else
                        status = "label_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String removeStudio(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String studio_id = props.get("studio_id");
                    if (util.studioExists(studio_id)) {
                        q.executeQuery("DELETE FROM album_studio WHERE studio_id = " + studio_id + ";");
                        q.executeQuery("DELETE FROM studio WHERE studio_id = " + studio_id + ";");
                    } else
                        status = "studio_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String removeMusician(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String musician_id = props.get("musician_id");
                    if (util.musicianExists(musician_id)) {
                        q.executeQuery("UPDATE song SET lyric_writer_id = NULL WHERE lyric_writer_id = " + musician_id + ";");
                        q.executeQuery("DELETE FROM song_composer WHERE musician_id = " + musician_id + ";");
                        q.executeQuery("DELETE FROM musician_role WHERE musician_id = " + musician_id + ";");
                        q.executeQuery("DELETE FROM musician WHERE musician_id = " + musician_id + ";");
                    } else
                        status = "musician_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    //</editor-fold>

    //<editor-fold desc="Update">

    // TODO: 28-11-2018 redo update

    private String updateArtist(HashMap<String, String> props) {
        String status = "ok";
        LinkedList<String> updates = new LinkedList<>();
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String artist_id = props.get("artist_id");

                    if (util.artistExists(artist_id)) {
                        if (props.containsKey("name")) {
                            updates.add("name = '" + props.get("name") + "'");
                        }
                        if (props.containsKey("description")) {
                            updates.add("description = '" + props.get("description") + "'");
                        }
                        String updateString = "";
                        for (String str : updates) {
                            updateString += str + ", ";
                        }
                        updateString = updateString.substring(0, updateString.length() - 2) + " ";
                        ResultSet rs_name = q.executeQuery(
                                "SELECT name " +
                                        "FROM artist " +
                                        "WHERE artist_id = " + artist_id + ";"
                        );
                        q.executeQuery("UPDATE artist " +
                                "SET " + updateString +
                                "WHERE artist_id = " + artist_id + ";");
                        q.executeQuery(
                                "INSERT INTO artist_editor(artist_id, editor_username) " +
                                        "VALUES (" + artist_id + ", '" + username + "') ON CONFLICT DO NOTHING;"
                        );
                        rs_name.next();
                        status += ";name:" + rs_name.getString(1);
                    } else
                        status = "artist_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String updateAlbum(HashMap<String, String> props) {
        String status = "ok";
        String changeArtist = "SET";
        LinkedList<String> updates = new LinkedList<>();
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String album_id = props.get("album_id");
                    if (util.albumExists(album_id)) {
                        String new_artist_id;
                        if (props.containsKey("artist_id")) {
                            new_artist_id = props.get("artist_id");
                            if (util.artistExists(new_artist_id)) {
                                updates.add("artist_id = " + new_artist_id);
                                changeArtist += " artist_id = " + new_artist_id + ",";
                            } else
                                status = "artist_null";
                        } else {
                            ResultSet rs_artist = q.executeQuery("SELECT artist_id FROM album WHERE album_id = " + album_id + ";");
                            rs_artist.next();
                            new_artist_id = rs_artist.getString(1);
                        }

                        String new_label_id;
                        if (props.containsKey("label_id")) {
                            new_label_id = props.get("label_id");
                            if (util.labelExists(new_label_id)) {
                                updates.add("label_id = " + new_label_id);
                            } else
                                status = "label_null";
                        }

                        if (props.containsKey("name")) {
                            updates.add("name = '" + props.get("name") + "'");
                        }
                        if (props.containsKey("description")) {
                            updates.add("description = '" + props.get("description") + "'");
                        }

                        if (status.equals("ok")) {
                            String updateString = "";
                            for (String str : updates) {
                                updateString += str + ", ";
                            }
                            updateString = updateString.substring(0, updateString.length() - 2) + " ";
                            if (!changeArtist.equals("SET")) {
                                changeArtist = changeArtist.substring(0, changeArtist.length() - 1) + " WHERE album_id = " + album_id + ";";
                                q.executeQuery("UPDATE album_studio " + changeArtist);
                                q.executeQuery("UPDATE review " + changeArtist);
                                q.executeQuery("UPDATE album_notification " + changeArtist);
                                q.executeQuery("UPDATE album_editor " + changeArtist);
                                q.executeQuery("UPDATE song_album " + changeArtist);
                            }
                            q.executeQuery("UPDATE album " +
                                    "SET " + updateString +
                                    "WHERE album_id = " + album_id + ";");
                            q.executeQuery(
                                    "INSERT INTO album_editor(artist_id, album_id, editor_username) " +
                                            "VALUES (" + new_artist_id + "," + album_id + ", '" + username + "') ON CONFLICT DO NOTHING"
                            );
                        }
                    } else
                        status = "album_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String changeTrackNum(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String album_id = props.get("album_id");
                    if (util.albumExists(album_id)) {
                        String song_id = props.get("song_id");
                        if (util.songExists(song_id)) {
                            if (util.existsInAlbum(album_id, song_id)) {
                                ResultSet rs_num = q.executeQuery(
                                        "SELECT track_num\n" +
                                                "FROM song_album\n" +
                                                "WHERE album_id = " + album_id + " AND song_id = " + song_id + ";"
                                );
                                rs_num.next();
                                String track_num = rs_num.getString(1);
                                String new_track_num = props.get("track_num");
                                if (Integer.valueOf(new_track_num) > Integer.valueOf(track_num)) {
                                    q.executeQuery(
                                            "UPDATE song_album\n" +
                                                    "SET track_num = track_num - 1\n" +
                                                    "WHERE album_id = " + album_id + "\n" +
                                                    "AND track_num > " + track_num + "\n" +
                                                    "AND track_num <= " + new_track_num + ";"
                                    );
                                } else if (Integer.valueOf(new_track_num) < Integer.valueOf(track_num)) {
                                    q.executeQuery(
                                            "UPDATE song_album\n" +
                                                    "SET track_num = track_num + 1\n" +
                                                    "WHERE album_id = " + album_id + "\n" +
                                                    "AND track_num < " + track_num + "\n" +
                                                    "AND track_num >= " + new_track_num + ";"
                                    );
                                }
                                q.executeQuery(
                                        "UPDATE song_album\n" +
                                                "SET track_num = " + new_track_num + "\n" +
                                                "WHERE album_id = " + album_id + "\n" +
                                                "AND song_id = " + song_id + ";"
                                );
                                q.executeQuery(
                                        "INSERT INTO album_editor(artist_id, album_id, editor_username) " +
                                                "VALUES (" + props.get("artist_id") + ", " + props.get("album_id") + ", '" + username + "') ON CONFLICT DO NOTHING "
                                );
                            } else
                                status = "doesnt_exist";
                        } else
                            status = "song_null";
                    } else
                        status = "album_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String updateSong(HashMap<String, String> props) {
        String status = "ok";
        LinkedList<String> updates = new LinkedList<>();
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String song_id = props.get("song_id");
                    if (util.songExists(song_id)) {
                        if (props.containsKey("name")) {
                            updates.add("name = '" + props.get("name") + "'");
                        }
                        if (props.containsKey("length")) {
                            updates.add("length = '" + props.get("length") + "'");
                        }
                        if (props.containsKey("genre")) {
                            updates.add("genre = '" + props.get("genre") + "'");
                        }
                        if (props.containsKey("lyrics")) {
                            updates.add("lyrics = '" + props.get("lyrics") + "'");
                        }
                        if (props.containsKey("lyric_writer_id")) {
                            if (util.musicianExists(props.get("lyric_writer_id"))) {
                                updates.add("lyric_writer_id = '" + props.get("lyric_writer_id") + "'");
                            } else
                                status = "lyric_writer_null";
                        }
                        if (status.equals("ok")) {
                            String updateString = "";
                            for (String str : updates) {
                                updateString += str + ", ";
                            }
                            updateString = updateString.substring(0, updateString.length() - 2) + " ";
                            ResultSet rs_name = q.executeQuery(
                                    "SELECT name " +
                                            "FROM song " +
                                            "WHERE song_id = " + song_id + ";"
                            );
                            q.executeQuery("UPDATE song " +
                                    "SET " + updateString +
                                    "WHERE song_id = " + song_id + ";");
                            q.executeQuery(
                                    "INSERT INTO song_editor(song_id, editor_username) " +
                                            "VALUES (" + song_id + ", '" + username + "') ON CONFLICT DO NOTHING;"
                            );
                            rs_name.next();
                            status += ";name:" + rs_name.getString(1);
                        }
                    } else
                        status = "song_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String updateLabel(HashMap<String, String> props) {
        String status = "ok";
        LinkedList<String> updates = new LinkedList<>();
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String label_id = props.get("label_id");

                    if (util.labelExists(label_id)) {
                        if (props.containsKey("name")) {
                            updates.add("name = '" + props.get("name") + "'");
                        }
                        if (props.containsKey("description")) {
                            updates.add("description = '" + props.get("description") + "'");
                        }
                        String updateString = "";
                        for (String str : updates) {
                            updateString += str + ", ";
                        }
                        updateString = updateString.substring(0, updateString.length() - 2) + " ";
                        ResultSet rs_name = q.executeQuery(
                                "SELECT name " +
                                        "FROM label " +
                                        "WHERE label_id = " + label_id + ";"
                        );
                        q.executeQuery("UPDATE label " +
                                "SET " + updateString +
                                "WHERE label_id = " + label_id + ";");
                        rs_name.next();
                        status += ";name:" + rs_name.getString(1);
                    } else
                        status = "label_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String updateStudio(HashMap<String, String> props) {
        String status = "ok";
        LinkedList<String> updates = new LinkedList<>();
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String studio_id = props.get("studio_id");

                    if (util.studioExists(studio_id)) {
                        if (props.containsKey("name")) {
                            updates.add("name = '" + props.get("name") + "'");
                        }
                        if (props.containsKey("description")) {
                            updates.add("description = '" + props.get("description") + "'");
                        }
                        String updateString = "";
                        for (String str : updates) {
                            updateString += str + ", ";
                        }
                        updateString = updateString.substring(0, updateString.length() - 2) + " ";
                        ResultSet rs_name = q.executeQuery(
                                "SELECT name " +
                                        "FROM studio " +
                                        "WHERE studio_id = " + studio_id + ";"
                        );
                        q.executeQuery("UPDATE studio " +
                                "SET " + updateString +
                                "WHERE studio_id = " + studio_id + ";");
                        rs_name.next();
                        status += ";name:" + rs_name.getString(1);
                    } else
                        status = "studio_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String updateMusician(HashMap<String, String> props) {
        String status = "ok";
        LinkedList<String> updates = new LinkedList<>();
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String musician_id = props.get("musician_id");

                    if (util.musicianExists(musician_id)) {
                        if (props.containsKey("name")) {
                            updates.add("name = '" + props.get("name") + "'");
                        }
                        if (props.containsKey("description")) {
                            updates.add("description = '" + props.get("description") + "'");
                        }
                        String updateString = "";
                        for (String str : updates) {
                            updateString += str + ", ";
                        }
                        updateString = updateString.substring(0, updateString.length() - 2) + " ";
                        ResultSet rs_name = q.executeQuery(
                                "SELECT name " +
                                        "FROM musician " +
                                        "WHERE musician_id = " + musician_id + ";"
                        );
                        q.executeQuery("UPDATE musician " +
                                "SET " + updateString +
                                "WHERE musician_id = " + musician_id + ";");
                        rs_name.next();
                        status += ";name:" + rs_name.getString(1);
                    } else
                        status = "musician_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String changeMusicianRole(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String artist_id = props.get("artist_id");
                    if (util.artistExists(artist_id)) {
                        String musician_id = props.get("musician_id");
                        if (util.musicianExists(musician_id)) {
                            if (util.musicianIsInArtist(musician_id, artist_id)) {
                                q.executeQuery(
                                        "UPDATE musician_role\n" +
                                                "SET role = " + props.get("role") + "\n" +
                                                "WHERE artist_id = " + artist_id + "\n" +
                                                "AND musician_id = " + musician_id + ";"
                                );
                            } else
                                status = "doesnt_exist";
                        } else
                            status = "musician_null";
                    } else
                        status = "artist_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }


    //</editor-fold>

    //<editor-fold desc="Add">

    private String addArtist(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    ResultSet rs_id = q.executeQuery("INSERT INTO artist (name" +
                            (props.containsKey("description") ? ",description" : "") +
                            ") " +
                            "VALUES ('" + props.get("name") + "'" +
                            (props.containsKey("description") ? ",'" + props.get("description") + "'" : "") +
                            ");");
                    rs_id.next();
                    int artist_id = rs_id.getInt(1);
                    q.executeQuery(
                            "INSERT INTO artist_editor(artist_id, editor_username) " +
                                    "VALUES (" + artist_id + ", '" + username + "')"
                    );
                    status += ";artist_id:" + artist_id;
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String addMusicianToArtist(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String artist_id = props.get("artist_id");
                    if (util.artistExists(artist_id)) {
                        String musician_id = props.get("musician_id");
                        if (util.musicianExists(musician_id)) {
                            if (!util.musicianIsInArtist(musician_id, artist_id)) {
                                q.executeQuery(
                                        "INSERT INTO musician_role (artist_id, musician_id, role) " +
                                                "VALUES (" + artist_id + ", " + musician_id + ", '" + props.get("role") + "');"
                                );
                                q.executeQuery(
                                        "INSERT INTO artist_editor(artist_id, editor_username) " +
                                                "VALUES (" + artist_id + ", '" + username + "') ON CONFLICT DO NOTHING "
                                );
                            } else
                                status = "already_exists";
                        } else
                            status = "musician_null";
                    } else
                        status = "artist_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String addAlbum(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    if (util.artistExists(props.get("artist_id"))) {
                        ResultSet rs_id = q.executeQuery("INSERT INTO album (name,artist_id)" +
                                "VALUES ('" + props.get("name") + "'," + props.get("artist_id") + ");");
                        rs_id.next();
                        int album_id = rs_id.getInt(1);
                        q.executeQuery(
                                "INSERT INTO album_editor(album_id, artist_id, editor_username) " +
                                        "VALUES (" + album_id + ", " + props.get("artist_id") + ", '" + username + "')"
                        );
                        status += ";album_id:" + album_id;
                    } else
                        status = "artist_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String addSongToAlbum(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String album_id = props.get("album_id");
                    if (util.albumExists(album_id)) {
                        String song_id = props.get("song_id");
                        if (util.songExists(song_id)) {
                            if (!util.existsInAlbum(album_id, song_id)) {
                                q.executeQuery(
                                        "INSERT INTO song_album (song_id, album_id, artist_id, track_num) " +
                                                "VALUES (" + song_id + ", " + album_id + "," +
                                                "(SELECT artist_id FROM album WHERE album_id = " + album_id + ")," +
                                                "(SELECT coalesce(max(track_num) + 1, 1) FROM song_album WHERE album_id = " + album_id + "))"
                                );
                                q.executeQuery(
                                        "INSERT INTO album_editor(artist_id, album_id, editor_username) " +
                                                "VALUES ((SELECT artist_id FROM album WHERE album_id = " + album_id + ")," + props.get("album_id") + ", '" + username + "') ON CONFLICT DO NOTHING "
                                );
                            } else
                                status = "already_exists";
                        } else
                            status = "song_null";
                    } else
                        status = "album_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String addStudioToAlbum(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String album_id = props.get("album_id");
                    if (util.albumExists(album_id)) {
                        String studio_id = props.get("studio_id");
                        if (util.studioExists(studio_id)) {
                            if (!util.wasRecordedIn(studio_id, album_id)) {
                                q.executeQuery(
                                        "INSERT INTO album_studio (studio_id, album_id, artist_id) " +
                                                "VALUES (" + studio_id + ", " + album_id + "," +
                                                "(SELECT artist_id FROM album WHERE album_id = " + album_id + "));"
                                );
                                q.executeQuery(
                                        "INSERT INTO album_editor(artist_id, album_id, editor_username) " +
                                                "VALUES (" + props.get("artist_id") + ", " + props.get("album_id") + ", '" + username + "') ON CONFLICT DO NOTHING "
                                );
                            } else
                                status = "already_exists";
                        } else
                            status = "studio_null";
                    } else
                        status = "album_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String addSong(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    ResultSet rs_id = q.executeQuery("INSERT INTO song (name" +
                            (props.containsKey("length") ? ",length" : "") +
                            (props.containsKey("genre") ? ",genre" : "") +
                            (props.containsKey("lyric_writer_id") ? ",lyric_writer_id" : "") +
                            ")" +
                            "VALUES ('" + props.get("name") + "'" +
                            (props.containsKey("length") ? "," + props.get("length") + "" : "") +
                            (props.containsKey("genre") ? ",'" + props.get("genre") + "'" : "") +
                            (props.containsKey("lyric_writer_id") ? "," + props.get("lyric_writer_id") : "") +
                            ")");
                    rs_id.next();
                    int song_id = rs_id.getInt(1);
                    q.executeQuery(
                            "INSERT INTO song_editor(song_id, editor_username) " +
                                    "VALUES (" + song_id + ", '" + username + "')"
                    );
                    status += ";song_id:" + song_id;
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String addComposerToSong(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    String song_id = props.get("song_id");
                    if (util.songExists(song_id)) {
                        String musician_id = props.get("musician_id");
                        if (util.musicianExists(musician_id)) {
                            if (!util.isComposer(musician_id, song_id)) {
                                q.executeQuery(
                                        "INSERT INTO song_composer (song_id, musician_id) " +
                                                "VALUES (" + song_id + ", " + musician_id + ");"
                                );
                                q.executeQuery(
                                        "INSERT INTO song_editor(song_id, editor_username) " +
                                                "VALUES (" + song_id + ", '" + username + "') ON CONFLICT DO NOTHING "
                                );
                            } else
                                status = "already_exists";
                        } else
                            status = "musician_null";
                    } else
                        status = "song_null";
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String addLabel(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    ResultSet rs_id = q.executeQuery("INSERT INTO label(name" +
                            (props.containsKey("description") ? ",description" : "") +
                            ") VALUES ('" + props.get("name") + "'" +
                            (props.containsKey("description") ? ",'" + props.get("description") + "'" : "") +
                            ");");
                    rs_id.next();
                    int label_id = rs_id.getInt(1);
                    status += ";label_id:" + label_id;
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String addStudio(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    ResultSet rs_id = q.executeQuery("INSERT INTO studio (name" +
                            (props.containsKey("description") ? ",description" : "") +
                            ") VALUES ('" + props.get("name") + "'" +
                            (props.containsKey("description") ? ",'" + props.get("description") + "'" : "") +
                            ");");
                    rs_id.next();
                    int studio_id = rs_id.getInt(1);
                    status += ";studio_id:" + studio_id;
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String addMusician(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                if (util.isEditor(username)) {
                    ResultSet rs_id = q.executeQuery("INSERT INTO musician (name" +
                            (props.containsKey("description") ? ",description" : "") +
                            ") VALUES ('" + props.get("name") + "'" +
                            (props.containsKey("description") ? ",'" + props.get("description") + "'" : "") +
                            ");");
                    rs_id.next();
                    int musician_id = rs_id.getInt(1);
                    status += ";musician_id:" + musician_id;
                } else
                    status = "not_editor";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    //</editor-fold>

    private String setDropBoxToken(HashMap<String, String> props) {
        String status = "ok";
        try {
            String username = props.get("username");
            if (util.userExists(username)) {
                q.executeQuery(
                        "UPDATE app_user\n" +
                                "SET dropbox_token = '" + props.get("token") + "'\n" +
                                "WHERE username = '" + username + "'"
                );
            } else
                status = "already_exists";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    //<editor-fold desc="Login/Register">

    private String login(HashMap<String, String> props) {
        String status = "ok";
        try {
            PasswordManager pm = new PasswordManager();
            String username = props.get("username");
            if (util.userExists(username)) {
                ResultSet rs_pw = q.executeQuery(
                        "SELECT pw_hash " +
                                "FROM app_user " +
                                "WHERE username = '" + username + "';");
                rs_pw.next();
                String pw_hash = rs_pw.getString(1);
                if (!pm.authenticate(props.get("password"), pw_hash))
                    status = "wrong_pass";
            } else
                status = "user_null";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    private String register(HashMap<String, String> props) {
        String status = "ok";
        try {
            PasswordManager pm = new PasswordManager();
            String username = props.get("username");
            if (!util.userExists(username)) {
                ResultSet rs = q.executeQuery("SELECT count(*) FROM app_user");
                rs.next();
                q.executeQuery(
                        "INSERT INTO app_user (username, pw_hash, is_editor) " +
                                "VALUES ('" + username + "', '" + pm.hash(props.get("password")) + "', " + (rs.getInt(1) == 0) + ");"
                );
            } else
                status = "already_exists";
        } catch (SQLException e) {
            e.printStackTrace();
            status = "sql_error";
        }
        return "status:" + status;
    }

    //</editor-fold>

}
