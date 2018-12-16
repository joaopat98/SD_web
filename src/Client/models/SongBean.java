package Client.models;

import Server.SearchSongs;
import Server.SongDetails;

import java.rmi.RemoteException;

public class SongBean extends Bean {

    public SongBean(UserBean userBean) {
        super(userBean);
    }

    public int newSong(String name) {
        while (true) {
            try {

                return server.addSong(username, name);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SearchSongs searchSongs(String query) {
        while (true) {
            try {
                return server.searchSong(username, query);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SearchSongs getAllSongs() {
        while (true) {
            try {
                return server.searchSong(username, "/");
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public SongDetails songDetails(int songId) {
        while (true) {
            try {
                return server.songDetails(username, songId);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public boolean editSong(int songID, String songname, int length, String lyrics, String genre) {
        String update = "name:" + songname + ";length:" + length + ";lyrics:" + lyrics + ";genre:" + genre;
        while (true) {
            try {
                return server.editSong(username, songID, update);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

    public boolean addToAlbum(int songID, int albumID) {
        while (true) {
            try {
                return server.addSongToAlbum(username, songID, albumID);
            } catch (RemoteException e) {
                reconnect();
            }
        }
    }

}
