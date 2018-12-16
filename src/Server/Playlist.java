package Server;

import java.io.Serializable;
import java.util.HashMap;

public class Playlist implements Serializable {
    int playlistID;
    String name;

    public Playlist(HashMap<String, String> props) {
        this.playlistID = Integer.valueOf(props.get("playlist_id"));
        this.name = props.get("name");
    }
}