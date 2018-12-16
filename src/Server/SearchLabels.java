package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchLabels implements Serializable{
    ArrayList<Label> labelList;

    public SearchLabels(HashMap<String, String> props) {
        createLabelList(props.get("labels"));
    }

    private void createLabelList(String message) {
        labelList = new ArrayList<>();
        for (HashMap<String, String> props : RMIServer.subPropertiesHashmap(message)) {
            labelList.add(new Label(props));
        }
    }

    public void printList() {
        if (!labelList.isEmpty()) {
            printLabels(labelList);
        } else
            System.out.println("No labels found.");
    }

    static void printLabels(ArrayList<Label> labelList) {
        for (int i = 0; i < labelList.size(); i++) {
            Label label = labelList.get(i);
            System.out.println(i+" - Label ID: " + label.labelID + "\tLabel name: " + label.name);
        }
    }

    public ArrayList<Label> getLabelList() {
        return labelList;
    }
}
