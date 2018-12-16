package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchStudios implements Serializable {
    ArrayList<Studio> studioList;

    public SearchStudios(HashMap<String, String> props) {
        createStudioList(props.get("studios"));
    }

    private void createStudioList(String message) {
        studioList = new ArrayList<>();
        for (HashMap<String, String> props : RMIServer.subPropertiesHashmap(message)) {
            studioList.add(new Studio(props));
        }
    }

    public void printList() {
        if (!studioList.isEmpty())
            for (int i = 0; i < studioList.size(); i++) {
                Studio studio = studioList.get(i);
                System.out.println(i + " - Studio ID: " + studio.studioID + "\tStudio name: " + studio.name);
            }
        else
            System.out.println("No studios found.");
    }

    public ArrayList<Studio> getStudioList() {
        return studioList;
    }
}
