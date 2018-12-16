package Client.models;

import Server.SearchArtists;
import Server.ArtistDetails;

import java.rmi.RemoteException;

public class ArtistBean extends Bean {

    public ArtistBean(UserBean userBean) {
        super(userBean);
    }

    public int newArtist(String name) {
        while (true) {
            try {

                return server.addArtist(username, name);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SearchArtists searchArtists(String query) {
        while (true) {
            try {
                return server.searchArtist(username, query);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SearchArtists getAllArtists() {
        while (true) {
            try {
                return server.searchArtist(username, "/");
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public ArtistDetails artistDetails(int artistId) {
        while (true) {
            try {
                return server.artistDetails(username, artistId);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public boolean editArtist(int artistID, String artistname, String description) {
        String update = "name:" + artistname + ";description:" + description;
        while (true) {
            try {
                return server.editArtist(username, artistID, update);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

}
