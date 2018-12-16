package Server;

import java.io.Serializable;
import java.util.HashMap;

public class Musician implements Serializable {
    int musicianID;
    String name;

    public Musician(HashMap<String, String> props) {
        this.musicianID = Integer.valueOf(props.get("musician_id"));
        this.name = props.get("name");
    }

    public Musician(ArtistMember member){
        this.musicianID = member.musician_id;
        this.name = member.name;
    }

    public int getMusicianID() {
        return musicianID;
    }

    public String getName() {
        return name;
    }

    public String getOption() {
        return musicianID + ": " + name;
    }
}