package Server;

import java.util.HashMap;

public class MusicianDetails {
    int musicianID;
    String name, description;

    public MusicianDetails(HashMap<String, String> props) {
        this.musicianID = Integer.valueOf(props.get("musician_id"));
        this.name = props.get("name");
        this.description = props.get("description");
    }

    void printInfo() {
        System.out.println("Musician ID: " + musicianID);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
    }

    public int getMusicianID() {
        return musicianID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
