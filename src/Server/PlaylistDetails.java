package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class PlaylistDetails implements Serializable {
    Integer playlistID;
    String playlistName, ownerUsername;
    ArrayList<Song> songList;
    boolean isPublic;

    private void createSongList(String message) {
        songList = new ArrayList<>();
        for (HashMap<String, String> props : RMIServer.subPropertiesHashmap(message))
            songList.add(new Song(props));
    }

    public PlaylistDetails(HashMap<String, String> props) {
        this.playlistID = Integer.valueOf(props.get("playlist_id"));
        this.playlistName = props.get("name");
        this.ownerUsername = props.get("owner_name");
        this.isPublic = props.get("is_public").toLowerCase().equals("true");
        createSongList(props.get("songs"));
    }

    public void printInfo() {
        System.out.println("Playlist ID: " + playlistID + "\n" +
                "Playlist name: " + playlistName + "\n" +
                "Playlist owner: " + ownerUsername + "\n" +
                "Public: " + (isPublic ? "yes" : "no"));
        if (!songList.isEmpty()) {
            printSongs(songList);
        } else
            System.out.println("No songs associated.");
    }

    static void printSongs(ArrayList<Song> songList) {
        System.out.println("Songs:");
        for (int i = 0; i < songList.size(); i++) {
            System.out.println(i + " - Song name: " + songList.get(i).name + "; Song ID: " + songList.get(i).songID);
        }
    }
}
