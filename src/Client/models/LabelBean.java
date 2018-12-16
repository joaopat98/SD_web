package Client.models;

import Server.SearchLabels;
import Server.LabelDetails;

import java.rmi.RemoteException;

public class LabelBean extends Bean {

    public LabelBean(UserBean userBean) {
        super(userBean);
    }

    public int newLabel(String name) {
        while (true) {
            try {

                return server.addLabel(username, name);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SearchLabels searchLabels(String query) {
        while (true) {
            try {
                return server.searchLabel(username, query);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SearchLabels getAllLabels() {
        while (true) {
            try {
                return server.searchLabel(username, "/");
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public LabelDetails labelDetails(int labelId) {
        while (true) {
            try {
                return server.labelDetails(username, labelId);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public boolean editLabel(int labelID, String labelName, String description) {
        String update = (labelName != null ? ";name:" + labelName : "") +
                (description != null ? ";description:" + description : "");
        update = update.substring(1);
        while (true) {
            try {
                return server.editLabel(username, labelID, update);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

}
