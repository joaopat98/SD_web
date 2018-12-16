/**
 * Raul Barbosa 2014-11-07
 */
package Client.actions;

import Server.SearchLabels;
import Server.LabelDetails;

public class LabelAction extends Action {
    private static String NO_USER = "no_user";
    private static String SERVER_ERROR = "server_error";
    private static String LABEL_NULL = "label_null";
    private static String NULL_FIELD = "null_field";


    private static final long serialVersionUID = 4L;
    private String labelname = null,
            query = null,
            description = null;
    private int labelID = -1;

    public String createLabel() {
        if (labelname != null && !labelname.equals("")) {

            if (getLabelBean().newLabel(labelname) != -1) {
                return SUCCESS;
            } else {
                return SERVER_ERROR;
            }
        } else
            return NULL_FIELD;
    }

    public String searchLabel() {
        if (query == null || query.equals(""))
            query = "/";
        SearchLabels labels;
        if ((labels = getLabelBean().searchLabels(query)) != null) {
            session.put("LabelSearch", labels);
            return SUCCESS;
        } else {
            return SERVER_ERROR;
        }
    }

    public String labelDetail() {
        if (labelID != -1) {
            LabelDetails label;
            if ((label = getLabelBean().labelDetails(labelID)) != null) {
                session.put("Label", label);
                return SUCCESS;
            } else {
                return LABEL_NULL;
            }
        } else
            return NULL_FIELD;
    }

    public String editLabel() {
        int labelID = ((LabelDetails) session.get("Label")).getLabelID();
        if (getLabelBean().editLabel(labelID, labelname, description)) {
            session.put("Label", getLabelBean().labelDetails(labelID));
            return SUCCESS;
        } else {
            return LABEL_NULL;
        }
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname; // will you sanitize this input? maybe use a prepared statement?
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setLabelID(int labelID) {
        this.labelID = labelID;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
