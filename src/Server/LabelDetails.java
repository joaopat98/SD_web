package Server;

import java.util.HashMap;

public class LabelDetails {
    int labelID;
    String name, description;

    public LabelDetails(HashMap<String, String> props) {
        this.labelID = Integer.valueOf(props.get("label_id"));
        this.name = props.get("name");
        this.description = props.get("description");
    }

    void printInfo() {
        System.out.println("Label ID: " + labelID);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
    }

    public int getLabelID() {
        return labelID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
