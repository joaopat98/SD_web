/**
 * Raul Barbosa 2014-11-07
 */
package Client.actions;

import Client.models.ArtistBean;
import Client.models.UserBean;
import Server.SearchArtists;
import Server.ArtistDetails;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class ArtistAction extends Action {
    private static String NO_USER = "no_user";
    private static String SERVER_ERROR = "server_error";
    private static String ARTIST_NULL = "artist_null";
    private static String NULL_FIELD = "null_field";


    private static final long serialVersionUID = 4L;
    private String artistname = null,
            query = null,
            description = null;
    private int artistID = -1;

    public String createArtist() {
        if (artistname != null && !artistname.equals("")) {

            if (getArtistBean().newArtist(artistname) != -1) {
                return SUCCESS;
            } else {
                return SERVER_ERROR;
            }
        } else
            return NULL_FIELD;
    }

    public String searchArtist() {
        if (query == null || query.equals(""))
            query = "/";
        SearchArtists artists;
        if ((artists = getArtistBean().searchArtists(query)) != null) {
            session.put("ArtistSearch", artists);
            return SUCCESS;
        } else {
            return SERVER_ERROR;
        }
    }

    public String artistDetail() {
        if (artistID != -1) {
            ArtistDetails artist;
            if ((artist = getArtistBean().artistDetails(artistID)) != null) {
                artist.printInfo();
                session.put("Artist", artist);
                return SUCCESS;
            } else {
                return ARTIST_NULL;
            }
        } else
            return NULL_FIELD;
    }

    public String editArtist() {
        int artistID = ((ArtistDetails) session.get("Artist")).getArtistID();
        if (getArtistBean().editArtist(artistID, artistname, description)) {
            session.put("Artist", getArtistBean().artistDetails(artistID));
            return SUCCESS;
        } else {
            return ARTIST_NULL;
        }
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname; // will you sanitize this input? maybe use a prepared statement?
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
