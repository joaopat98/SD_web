/**
 * Raul Barbosa 2014-11-07
 */
package Client.actions;

import Server.SearchMusicians;
import Server.MusicianDetails;

public class MusicianAction extends Action {
    private static String NO_USER = "no_user";
    private static String SERVER_ERROR = "server_error";
    private static String MUSICIAN_NULL = "musician_null";
    private static String NULL_FIELD = "null_field";


    private static final long serialVersionUID = 4L;
    private String musicianname = null,
            query = null,
            description = null;
    private int musicianID = -1;

    public String createMusician() {
        if (musicianname != null && !musicianname.equals("")) {

            if (getMusicianBean().newMusician(musicianname) != -1) {
                return SUCCESS;
            } else {
                return SERVER_ERROR;
            }
        } else
            return NULL_FIELD;
    }

    public String searchMusician() {
        if (query == null || query.equals(""))
            query = "/";
        SearchMusicians musicians;
        if ((musicians = getMusicianBean().searchMusicians(query)) != null) {
            session.put("MusicianSearch", musicians);
            return SUCCESS;
        } else {
            return SERVER_ERROR;
        }
    }

    public String musicianDetail() {
        if (musicianID != -1) {
            MusicianDetails musician;
            if ((musician = getMusicianBean().musicianDetails(musicianID)) != null) {
                session.put("Musician", musician);
                return SUCCESS;
            } else {
                return MUSICIAN_NULL;
            }
        } else
            return NULL_FIELD;
    }

    public String editMusician() {
        int musicianID = ((MusicianDetails) session.get("Musician")).getMusicianID();
        if (getMusicianBean().editMusician(musicianID, musicianname, description)) {
            session.put("Musician", getMusicianBean().musicianDetails(musicianID));
            return SUCCESS;
        } else {
            return MUSICIAN_NULL;
        }
    }

    public void setMusicianname(String musicianname) {
        this.musicianname = musicianname; // will you sanitize this input? maybe use a prepared statement?
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setMusicianID(int musicianID) {
        this.musicianID = musicianID;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
