package Client.models;

import Server.SearchMusicians;
import Server.MusicianDetails;

import java.rmi.RemoteException;

public class MusicianBean extends Bean {

    public MusicianBean(UserBean userBean) {
        super(userBean);
    }

    public int newMusician(String name) {
        while (true) {
            try {

                return server.addMusician(username, name);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SearchMusicians searchMusicians(String query) {
        while (true) {
            try {
                return server.searchMusician(username, query);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SearchMusicians getAllMusicians() {
        while (true) {
            try {
                return server.searchMusician(username, "/");
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public MusicianDetails musicianDetails(int musicianId) {
        while (true) {
            try {
                return server.musicianDetails(username, musicianId);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public boolean editMusician(int musicianID, String musicianName, String description) {
        String update = (musicianName != null ? ";name:" + musicianName : "") +
                (description != null ? ";description:" + description : "");
        update = update.substring(1);
        while (true) {
            try {
                return server.editMusician(username, musicianID, update);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

}
