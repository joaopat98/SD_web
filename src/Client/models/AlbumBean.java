package Client.models;

import Server.SearchAlbums;
import Server.AlbumDetails;

import java.rmi.RemoteException;

public class AlbumBean extends Bean {

    public AlbumBean(UserBean userBean) {
        super(userBean);
    }

    public int newAlbum(String name, int artistID) {
        while (true) {
            try {

                return server.addAlbum(username, name, artistID);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SearchAlbums searchAlbums(String query) {
        while (true) {
            try {
                return server.searchAlbum(username, query);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SearchAlbums getAllAlbums() {
        while (true) {
            try {
                return server.searchAlbum(username, "/");
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public AlbumDetails albumDetails(int albumId) {
        while (true) {
            try {
                return server.albumDetails(username, albumId);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public boolean editAlbum(int albumID, String albumName, String description, int artistID, int labelID) {
        String update = (albumName != null ? ";name:" + albumName : "") +
                (description != null ? ";description:" + description : "") +
                (artistID != -1 ? ";artist_id:" + artistID : "") +
                (labelID != -1 ? ";label_id:" + labelID : "");
        update = update.substring(1);
        while (true) {
            try {
                return server.editAlbum(username, albumID, update);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

}
