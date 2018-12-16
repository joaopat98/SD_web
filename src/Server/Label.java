package Server;

import java.io.Serializable;
import java.util.HashMap;

public class Label implements Serializable {
    int labelID;
    String name;

    public Label(HashMap<String, String> props) {
        this.labelID = Integer.valueOf(props.get("label_id"));
        this.name = props.get("name");
    }

    public int getLabelID() {
        return labelID;
    }

    public String getName() {
        return name;
    }

    public String getOption() {
        return labelID + ": " + name;
    }
}