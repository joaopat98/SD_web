package Server;

import java.util.HashMap;

public class StudioDetails {
    int studioID;
    String name, description;

    public StudioDetails(HashMap<String, String> props) {
        this.studioID = Integer.valueOf(props.get("studio_id"));
        this.name = props.get("name");
        this.description = props.get("description");
    }

    void printInfo() {
        System.out.println("Studio ID: " + studioID);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
    }

    public int getStudioID() {
        return studioID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
