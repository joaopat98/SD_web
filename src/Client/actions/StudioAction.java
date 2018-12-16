/**
 * Raul Barbosa 2014-11-07
 */
package Client.actions;

import Server.StudioDetails;
import Server.SearchStudios;

public class StudioAction extends Action {
    private static String NO_USER = "no_user";
    private static String SERVER_ERROR = "server_error";
    private static String STUDIO_NULL = "studio_null";
    private static String NULL_FIELD = "null_field";


    private static final long serialVersionUID = 4L;
    private String studioname = null,
            query = null,
            description = null;
    private int studioID = -1;

    public String createStudio() {
        if (studioname != null && !studioname.equals("")) {

            if (getStudioBean().newStudio(studioname) != -1) {
                return SUCCESS;
            } else {
                return SERVER_ERROR;
            }
        } else
            return NULL_FIELD;
    }

    public String searchStudio() {
        if (query == null || query.equals(""))
            query = "/";
        SearchStudios studios;
        if ((studios = getStudioBean().searchStudios(query)) != null) {
            session.put("StudioSearch", studios);
            return SUCCESS;
        } else {
            return SERVER_ERROR;
        }
    }

    public String studioDetail() {
        if (studioID != -1) {
            StudioDetails studio;
            if ((studio = getStudioBean().studioDetails(studioID)) != null) {
                session.put("Studio", studio);
                return SUCCESS;
            } else {
                return STUDIO_NULL;
            }
        } else
            return NULL_FIELD;
    }

    public String editStudio() {
        int studioID = ((StudioDetails) session.get("Studio")).getStudioID();
        if (getStudioBean().editStudio(studioID, studioname, description)) {
            session.put("Studio", getStudioBean().studioDetails(studioID));
            return SUCCESS;
        } else {
            return STUDIO_NULL;
        }
    }

    public void setStudioname(String studioname) {
        this.studioname = studioname; // will you sanitize this input? maybe use a prepared statement?
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setStudioID(int studioID) {
        this.studioID = studioID;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
