package Server;

import java.io.Serializable;
import java.util.HashMap;

public class Artist implements Serializable {
    int artistID;
    String name;

    public Artist(HashMap<String, String> props) {
        this.artistID = Integer.valueOf(props.get("artist_id"));
        this.name = props.get("name");
    }

    public int getArtistID() {
        return artistID;
    }

    public String getName() {
        return name;
    }

    public String getOption() {
        return artistID + ": " + name;
    }
}