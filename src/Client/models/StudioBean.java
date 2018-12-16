package Client.models;

import Server.StudioDetails;
import Server.SearchStudios;

import java.rmi.RemoteException;

public class StudioBean extends Bean {

    public StudioBean(UserBean userBean) {
        super(userBean);
    }

    public int newStudio(String name) {
        while (true) {
            try {

                return server.addStudio(username, name);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SearchStudios searchStudios(String query) {
        while (true) {
            try {
                return server.searchStudio(username, query);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SearchStudios getAllStudios() {
        while (true) {
            try {
                return server.searchStudio(username, "/");
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public StudioDetails studioDetails(int studioId) {
        while (true) {
            try {
                return server.studioDetails(username, studioId);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public boolean editStudio(int studioID, String studioName, String description) {
        String update = (studioName != null ? ";name:" + studioName : "") +
                (description != null ? ";description:" + description : "");
        update = update.substring(1);
        while (true) {
            try {
                return server.editStudio(username, studioID, update);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

}
