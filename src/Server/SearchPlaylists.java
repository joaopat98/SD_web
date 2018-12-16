package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchPlaylists implements Serializable{
    ArrayList<Playlist> playlistList;

    public SearchPlaylists(HashMap<String, String> props) {
        createPlaylistList(props.get("playlists"));
    }

    private void createPlaylistList(String message) {
        playlistList = new ArrayList<>();
        for (HashMap<String, String> props : RMIServer.subPropertiesHashmap(message)) {
            playlistList.add(new Playlist(props));
        }
    }

    static void printPlaylists(ArrayList<Playlist> playlistList) {
        for (int i = 0; i < playlistList.size(); i++) {
            Playlist playlist = playlistList.get(i);
            System.out.println(i+" - Playlist ID: " + playlist.playlistID + "\tPlaylist name: " + playlist.name);
        }
    }
}
