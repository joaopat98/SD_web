package Server;

import java.io.Serializable;
import java.util.HashMap;

public class ArtistMember implements Serializable{
    String name;
    int musician_id;

    public ArtistMember(HashMap<String,String> props) {
        this.name = props.get("name");
        this.musician_id = Integer.valueOf(props.get("musician_id"));
    }

    public String getName() {
        return name;
    }

    public int getMusician_id() {
        return musician_id;
    }
}
