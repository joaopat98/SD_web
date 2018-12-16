/**
 * Raul Barbosa 2014-11-07
 */
package Client.actions;

import Client.models.AlbumBean;
import Client.models.UserBean;
import Server.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;
import java.util.Map;

public class AlbumAction extends Action {
    private static String NO_USER = "no_user";
    private static String SERVER_ERROR = "server_error";
    private static String ALBUM_NULL = "album_null";
    private static String NULL_FIELD = "null_field";


    private static final long serialVersionUID = 4L;
    private String albumName = null,
            query = null,
            description = null;
    private int albumID = -1, artistID = -1, labelID = -1, songID = -1;

    private ArrayList<Song> getSelectableSongs(AlbumDetails album) {
        ArrayList<Song> selectable = new ArrayList<>();
        for(Song song : getSongBean().getAllSongs().getSongList()) {
            boolean selected = false;
            for (Song selectedSong: album.getSongList()) {
                if (song.getSongID() == selectedSong.getSongID()) {
                    selected = true;
                    break;
                }
            }
            if(!selected) selectable.add(song);
        }
        return selectable;
    }

    public String createAlbum() {
        int artistID = ((ArtistDetails) session.get("Artist")).getArtistID();
        if (albumName != null && !albumName.equals("")) {

            if (getAlbumBean().newAlbum(albumName, artistID) != -1) {
                session.put("Artist", getArtistBean().artistDetails(artistID));
                return SUCCESS;
            } else {
                return SERVER_ERROR;
            }
        } else
            return NULL_FIELD;
    }

    public String searchAlbum() {
        if (query == null || query.equals(""))
            query = "/";
        SearchAlbums albums;
        if ((albums = getAlbumBean().searchAlbums(query)) != null) {
            session.put("AlbumSearch", albums);
            return SUCCESS;
        } else {
            return SERVER_ERROR;
        }
    }

    public String albumDetail() {
        if (albumID != -1) {
            AlbumDetails album;
            if ((album = getAlbumBean().albumDetails(albumID)) != null) {
                ArrayList<Song> selectable = getSelectableSongs(album);

                session.put("SongList", selectable);
                session.put("Album", album);
                return SUCCESS;
            } else {
                return ALBUM_NULL;
            }
        } else
            return NULL_FIELD;
    }

    public String editAlbum() {
        int albumID = ((AlbumDetails) session.get("Album")).getAlbumID();
        if (getAlbumBean().editAlbum(albumID, albumName, description, artistID, labelID)) {
            session.put("Album", getAlbumBean().albumDetails(albumID));
            return SUCCESS;
        } else {
            return ALBUM_NULL;
        }
    }

    public String loadAlbumEditMenu() {
        session.put("ArtistList", getArtistBean().getAllArtists().getArtistList());
        session.put("LabelList", getLabelBean().getAllLabels().getLabelList());
        return SUCCESS;
    }

    public String addSongToAlbum() {
        if (songID != -1) {
            int albumID = ((AlbumDetails) session.get("Album")).getAlbumID();
            if (getSongBean().addToAlbum(songID, albumID)) {
                AlbumDetails album = getAlbumBean().albumDetails(albumID);
                session.put("SongList", getSelectableSongs(album));
                session.put("Album", album);
            } else return SERVER_ERROR;
        }
        return SUCCESS;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName; // will you sanitize this input? maybe use a prepared statement?
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public void setLabelID(int labelID) {
        this.labelID = labelID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }
}
