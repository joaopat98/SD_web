package Server;

import java.io.Serializable;
import java.util.HashMap;

public class Studio implements Serializable {
    String name;
    int studioID;

    public Studio(HashMap<String, String> props) {
        this.name = props.get("name");
        this.studioID = Integer.valueOf(props.get("studio_id"));
    }

    public String getName() {
        return name;
    }

    public int getStudioID() {
        return studioID;
    }

    public String getOption() {
        return studioID + ": " + name;
    }
}
