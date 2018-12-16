/**
 * Raul Barbosa 2014-11-07
 */
package Client.actions;

import Client.models.SongBean;
import Client.models.UserBean;
import Server.AlbumDetails;
import Server.SearchSongs;
import Server.SongDetails;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class SongAction extends Action {
    private static String NO_USER = "no_user";
    private static String SERVER_ERROR = "server_error";
    private static String SONG_NULL = "song_null";
    private static String NULL_FIELD = "null_field";


    private static final long serialVersionUID = 4L;
    private String songname = null,
            query = null,
            lyrics = null,
            genre = null;
    private int songID = -1, length = -1;

    public String createSong() {
        if (songname != null && !songname.equals("")) {

            if (getSongBean().newSong(songname) != -1) {
                return SUCCESS;
            } else {
                return SERVER_ERROR;
            }
        } else
            return NULL_FIELD;
    }

    public String searchSong() {
        if (query == null || query.equals(""))
            query = "/";
        SearchSongs songs;
        if ((songs = getSongBean().searchSongs(query)) != null) {
            session.put("SongSearch", songs);
            return SUCCESS;
        } else {
            return SERVER_ERROR;
        }
    }

    public String songDetail() {
        if (songID != -1) {
            SongDetails song;
            if ((song = getSongBean().songDetails(songID)) != null) {
                song.printInfo();
                session.put("Song", song);
                return SUCCESS;
            } else {
                return SONG_NULL;
            }
        } else
            return NULL_FIELD;
    }

    public String editSong() {
        int songID = ((SongDetails) session.get("Song")).getSongID();
        if (getSongBean().editSong(songID, songname, length, lyrics, genre)) {
            session.put("Song", getSongBean().songDetails(songID));
            return SUCCESS;
        } else {
            return SONG_NULL;
        }
    }

    public void setSongname(String songname) {
        this.songname = songname; // will you sanitize this input? maybe use a prepared statement?
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
